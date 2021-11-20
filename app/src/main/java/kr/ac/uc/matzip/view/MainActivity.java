package kr.ac.uc.matzip.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.model.TokenModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.MemberAPI;
import kr.ac.uc.matzip.presenter.TokenAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Permission permission = new Permission(this);

    Button regBtn, mapBtn, loginBtn, imgBtn, boardBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SaveSharedPreference.init(getApplicationContext());

        permission.check();

        regBtn = findViewById(R.id.regBtn);
        mapBtn = findViewById(R.id.mapBtn);
        loginBtn = findViewById(R.id.loginBtn);
        imgBtn = findViewById(R.id.imgBtn);
        boardBtn = findViewById(R.id.boardBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMapToBoardActivity.class);
                startActivity(intent);
            }
        });

        boardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BoardActivity.class);
                startActivity(intent);
            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImgBoardActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut(0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogOut(1);
    }

    private void LogOut(int destroy){
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.logOut(destroy).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                MemberModel res = response.body();

                if(res.getSuccess() == "true")
                {
                    SaveSharedPreference.clear();
                    Log.d(TAG, "로그아웃");
                }
                else if (res.getSuccess() == "false" && destroy == 1)
                {
                    Log.d(TAG, "자동로그인");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}