package kr.ac.uc.matzip.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardActivity extends AppCompatActivity {
    private static final String TAG = "BoardActivity";
    private EditText bo_title, bo_cont;
    private Button btn_board;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_board);

        bo_title = (EditText) findViewById(R.id.bo_title);
        bo_cont = (EditText) findViewById(R.id.bo_cont);

        btn_board = (Button) findViewById(R.id.btn_board);

        btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SaveSharedPreference.checkLogin() == true) {
                    Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                    startActivity(intent);
                    postBoard();
                }
                else{
                    Log.d(TAG, "onClick: " + SaveSharedPreference.getString("token"));
                    Intent intent = new Intent(BoardActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void postBoard()
    {
        final String title = bo_title.getText().toString();
        final String cont = bo_cont.getText().toString();

        BoardAPI boardAPI = ApiClient.getApiClient().create(BoardAPI.class);
        boardAPI.postData(SaveSharedPreference.getInt("id"), title, cont).enqueue(new Callback<BoardModel>()
        {
            @Override
            public void onResponse(@NonNull Call<BoardModel> call,@NonNull Response<BoardModel> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"글 작성에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BoardModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
