package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.CheckTokenModel;
import kr.ac.uc.matzip.model.TokenModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TokenAPI {
    @FormUrlEncoded
    @POST("token.php")
    Call<TokenModel> getToken(@Field("username") String username,
                            @Field("autolog") Integer autolog);

    @POST("check_token.php")
    Call<CheckTokenModel> check_Token();
}
