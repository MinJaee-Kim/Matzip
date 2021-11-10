package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.MemberModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TokenAPI {
    @FormUrlEncoded
    @POST("token.php")
    Call<MemberModel> getToken(@Field("username") String username);
}
