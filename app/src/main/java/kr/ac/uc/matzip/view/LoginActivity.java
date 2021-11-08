package kr.ac.uc.matzip.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.MemberAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login, btn_register, btn_autoLog;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        SaveSharedPreference saveSharedPreference = new SaveSharedPreference();

        et_id = findViewById(R.id.log_IDEt);
        et_pass = findViewById(R.id.log_pwEt);
        btn_login = findViewById(R.id.log_loginBtn);
        btn_register = findViewById(R.id.log_regeBtn);
        btn_autoLog = findViewById(R.id.log_autoLog);

        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                Login();
            }
        });

        btn_autoLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSharedPreference.setUserName(LoginActivity.this , et_id.getText().toString(), et_pass.getText().toString());
                Log.d(TAG, "onClick: " + SaveSharedPreference.getUserName(LoginActivity.this));
                Log.d(TAG, "onClick: " + SaveSharedPreference.getUserPW(LoginActivity.this));
                AutoLogin();
            }
        });
    }

    private void Login(){
        final String userID = et_id.getText().toString();
        final String userPass = et_pass.getText().toString();
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.getLogin(userID).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                String HashPw = SaveSharedPreference.getUserPW(LoginActivity.this);
                boolean checkPw = BCrypt.checkpw(userPass, HashPw);
                Log.d(TAG, "onResponse: ff");
                if(response.isSuccessful() && checkPw)
                {
                    Toast.makeText(getApplicationContext(),"로그인 성공하였습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"로그인 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void AutoLogin(){
        final String userID = et_id.getText().toString();
        final String userPass = et_pass.getText().toString();
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.getLogin(SaveSharedPreference.getUserName(LoginActivity.this)).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                String HashPw = response.body().getPassword();
                boolean checkPw = BCrypt.checkpw(userPass, HashPw);
                Log.d(TAG, "onResponse: ff");
                if(response.isSuccessful() && checkPw)
                {
                    Toast.makeText(getApplicationContext(),"로그인 성공하였습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"로그인 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
