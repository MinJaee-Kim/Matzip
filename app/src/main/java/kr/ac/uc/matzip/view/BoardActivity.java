package kr.ac.uc.matzip.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

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
                Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                startActivity(intent);
                postBoard();
            }
        });
    }

    private void postBoard()
    {
        final String title = bo_title.getText().toString();
        final String cont = bo_cont.getText().toString();

        HashMap<String, Object> input = new HashMap<>();
        input.put("bo_title", title);
        input.put("bo_cont", cont);

        BoardAPI boardAPI = ApiClient.getApiClient().create(BoardAPI.class);
        boardAPI.postData(input).enqueue(new Callback<BoardModel>() {
            @Override
            public void onResponse(Call<BoardModel> call, Response<BoardModel> response) {
                if(response.isSuccessful())
                {
                    BoardModel data = response.body();

                    Log.e("onSuccess", data.getBo_title());

                    Toast.makeText(getApplicationContext(),"글 작성에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BoardModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
