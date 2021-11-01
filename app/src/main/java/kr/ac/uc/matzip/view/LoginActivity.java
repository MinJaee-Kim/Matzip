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
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.MemberAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button btn_login, btn_register;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        et_id = findViewById(R.id.log_IDEt);
        et_pass = findViewById(R.id.log_pwEt);
        btn_login = findViewById(R.id.log_loginBtn);
        btn_register = findViewById(R.id.log_regeBtn);


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
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
                memberAPI.getLogin(userID, userPass).enqueue(new Callback<MemberModel>()
                {
                    @Override
                    public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                        Log.d(TAG, "onResponse: ff");
                        if(response.body().getSuccess())
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

//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//
////                        try {
////                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
////                            System.out.println("hongchul" + response);
////                            JSONObject jsonObject = new JSONObject(response);
////                            boolean success = jsonObject.getBoolean("success");
////                            if (success) { // 로그인에 성공한 경우
////                                String userID = jsonObject.getString("username");
////                                String userPass = jsonObject.getString("password");
////
////                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
////                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                                intent.putExtra("username", userID);
////                                intent.putExtra("password", userPass);
////                                startActivity(intent);
////                            } else { // 로그인에 실패한 경우
////                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
////                                return;
////                            }
////                        } catch (JSONException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                };
//                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(loginRequest);
            }
        });
    }
}
