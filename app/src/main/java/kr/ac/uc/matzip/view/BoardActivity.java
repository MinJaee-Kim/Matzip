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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private BoardModel mBoardModel;

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

        BoardAPI boardAPI = ApiClient.getApiClient().create(BoardAPI.class);
        Call<String> call = boardAPI.postData(title, cont);
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                if(response.isSuccessful())
                {
                    String jsonObject = response.body();

                    Log.e("onSuccess", jsonObject);

                    Toast.makeText(getApplicationContext(),"글 작성에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    parsePost(jsonObject);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void parsePost(String response){
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true"))
            {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++)
                {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    mBoardModel.putTitle(dataobj.getString("bo_title"));
                    mBoardModel.putCont(dataobj.getString("bo_cont"));
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
