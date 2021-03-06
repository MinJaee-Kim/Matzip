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
import kr.ac.uc.matzip.model.KakaoModel;
import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.KakaoAPI;
import kr.ac.uc.matzip.presenter.MemberAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Button regBtn, mapBtn, loginBtn, imgBtn, boardBtn, logoutBtn, viewpagerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SaveSharedPreference.init(getApplicationContext());

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
                Intent intent = new Intent(MainActivity.this, AddMapToBoardFragment.class);
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

        KakaoAPI kakaoAPI = ApiClient.kakaoSearchApiClient().create(KakaoAPI.class);
        kakaoAPI.searchAddressList("KakaoAK 6bcf1f1f97b9ae55f0878c7378b29037", "??????").enqueue(new Callback<KakaoModel>() {
            @Override
            public void onResponse(Call<KakaoModel> call, Response<KakaoModel> response) {
                String res = response.body().getDocuments().get(0).getPlace_name();

                Log.d(TAG, "onResponse: " + res);
            }

            @Override
            public void onFailure(Call<KakaoModel> call, Throwable t) {
                Log.e(TAG, "onResponse : " + t.getMessage());
            }
        });
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void LogOut(int destroy){
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.logOut(destroy).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                MemberModel res = response.body();

                if(res.getSuccess().equals("true"))
                {
                    Logout_History(res.getUser_id(), res.getToken_value());
                    Log.d(TAG, "logout user_id, token : " + +res.getUser_id() + res.getToken_value());
                    SaveSharedPreference.clear();
                    Log.d(TAG, "????????????");
                    Toast.makeText(getApplicationContext(),"???????????? ???????????????.",Toast.LENGTH_SHORT).show();
                }
                else if (res.getSuccess() == "false" && destroy == 1)
                {
                    Log.d(TAG, "???????????????");
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
                Log.e(TAG, "Logout_History onFailure: " + t.getMessage() + user_id + "  " + token_value);
            }
        });
    }
}