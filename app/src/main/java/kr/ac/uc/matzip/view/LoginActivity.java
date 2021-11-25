package kr.ac.uc.matzip.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
    private Button btn_login;
    private TextView btn_register;
    private CheckBox log_check;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_design);

        et_id = findViewById(R.id.login_usernameEt);
        et_pass = findViewById(R.id.login_passwordEt);
        btn_login = findViewById(R.id.login_logBtn);
        btn_register = findViewById(R.id.login_accountTv);
        log_check = findViewById(R.id.login_autoCb);

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
                if(et_id.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    if (et_pass.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                        if (log_check.isChecked()) {
                            Login(1);
                        } else if (!log_check.isChecked()) {
                            Login(0);
                        }
                    }
                }
            }
        });
    }

    private void Login(Integer is_auto_log){
        final String userID = et_id.getText().toString();
        final String userPass = et_pass.getText().toString();
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.getLogin(userID, is_auto_log).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                MemberModel res = response.body();
                String HashPw = res.getPassword();
                boolean checkPw = BCrypt.checkpw(userPass, HashPw);
                Log.d(TAG, "onResponse: ff");
                if(response.isSuccessful() && checkPw)
                {
                    SaveSharedPreference.setString("token", res.getToken_value());
                    Log.d(TAG, "Login get Token: " + res.getToken_value());
                    Toast.makeText(getApplicationContext(),"로그인 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText(getApplicationContext(),"로그인 실패하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(),"로그인 실패하였습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
