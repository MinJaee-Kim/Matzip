package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.TokenModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TokenAPI {
    @FormUrlEncoded
    @POST("token.php")
    Call<TokenModel> getToken(@Field("username") String username,
                            @Field("autolog") Integer autolog);

    @FormUrlEncoded
    @POST("check_token.php")
    Call<TokenModel> check_Token(@Field("token") String token);

    @FormUrlEncoded
    @POST("token_logout.php")
    Call<TokenModel> logout_Token(@Field("token") String token);
}
