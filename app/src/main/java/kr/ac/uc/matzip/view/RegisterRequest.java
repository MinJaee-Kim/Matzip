package kr.ac.uc.matzip.view;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://150.230.131.84/register.php";
    private Map<String, String> map;


    public RegisterRequest(String username, String password, String nickname, Response.Listener<String> listener) {
        super(Method.POST ,URL, listener, null);

        map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("nickname", nickname);
//        map.put("authority", nickname);
//        map.put("enabled", nickname);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
