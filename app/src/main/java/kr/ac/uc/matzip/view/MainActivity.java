package kr.ac.uc.matzip.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.MemberAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Permission permission = new Permission(this);

    Button regBtn, mapBtn, loginBtn, imgBtn, boardBtn, logoutBtn, viewpagerbtn;

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
        viewpagerbtn = findViewById(R.id.vpBtn);


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
                Intent intent = new Intent(MainActivity.this, BoardListActivity.class);
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

        viewpagerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
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
                    Logout_History(res.getUser_id(), res.getToken_value());
                    SaveSharedPreference.clear();
                    Log.d(TAG, "로그아웃");
                    Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                }
                else if (res.getSuccess() == "false" && destroy == 1)
                {
                    Log.d(TAG, "자동로그인");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "LogOut : " + t.getMessage());
            }
        });
    }

    private void Logout_History(int user_id, String token_value){
        MemberAPI memberAPI = ApiClient.getNoHeaderApiClient().create(MemberAPI.class);
        memberAPI.update_logout_history(user_id, token_value).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                Log.d(TAG, "Logout_History onResponse: " + token_value);
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "Logout_History onFailure: " + t.getMessage() + token_value);
            }
        });
    }
}