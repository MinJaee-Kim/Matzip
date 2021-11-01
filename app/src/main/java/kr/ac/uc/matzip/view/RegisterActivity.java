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

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Reg";

    private EditText et_id, et_pw, et_nickname;
    private Button btn_OK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_page);

        et_id = findViewById(R.id.log_IDEt);
        et_pw = findViewById(R.id.log_pwEt);
        et_nickname = findViewById(R.id.et_nickname);

        btn_OK = findViewById(R.id.log_loginBtn);

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
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

        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.regMember(username, passwordHashed, nickname).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
