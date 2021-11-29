package kr.ac.uc.matzip.view;

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

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Reg";

    private EditText et_id, et_pw, et_nickname;
    private Button btn_OK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_design);

        et_id = findViewById(R.id.account_usernameEt);
        et_pw = findViewById(R.id.account_passwordEt);
        et_nickname = findViewById(R.id.account_nicknameEt);

        btn_OK = findViewById(R.id.account_createBtn);

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_id.length() >= 5 && et_pw.length() >= 7 && et_nickname.length() >= 2){
                    user_overlap_check();
                }else if(et_id.length() < 5){
                    Toast.makeText(getApplicationContext(),"최소 아이디는 5글자 입니다",Toast.LENGTH_SHORT).show();
                }else if(et_pw.length() < 7){
                    Toast.makeText(getApplicationContext(),"최소 비밀번호는 7글자 입니다.",Toast.LENGTH_SHORT).show();
                }else if(et_nickname.length() < 2){
                    Toast.makeText(getApplicationContext(),"최소 닉네임은 2글자 입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void user_overlap_check()
    {
        final String username = et_id.getText().toString();
        MemberAPI memberAPI = ApiClient.getNoHeaderApiClient().create(MemberAPI.class);
        memberAPI.user_overlap_check(username).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                MemberModel res = response.body();
                Toast.makeText(getApplicationContext(),res.getMessage() ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                regMember();
            }
        });
    }

    private void regMember()
    {
        final String username = et_id.getText().toString();
        final String password = et_pw.getText().toString();
        final String nickname = et_nickname.getText().toString();
        final String passwordHashed = BCrypt.hashpw(password, BCrypt.gensalt(10));

        MemberAPI memberAPI = ApiClient.getNoHeaderApiClient().create(MemberAPI.class);
        memberAPI.regMember(username, passwordHashed, nickname).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "regMember : " + t.getMessage());
            }
        });
    }
}
