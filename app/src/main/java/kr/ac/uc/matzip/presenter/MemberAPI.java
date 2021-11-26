package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.MemberModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MemberAPI {
    @FormUrlEncoded
    @POST("register.php")
    Call<MemberModel> regMember (@Field("username") String username,
                                @Field("password") String password,
                                @Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("login.php") // @전송방식(데이터를 전송할 서버 파일명)
    Call<MemberModel> getLogin(@Field("username") String username
                                ,@Field("is_auto_log") Integer is_auto_log); // Call<응답받을 데이터형> 함수명(서버에 전달할 데이터)

    @FormUrlEncoded
    @POST("token_logout.php") // @전송방식(데이터를 전송할 서버 파일명)
    Call<MemberModel> logOut(@Field("destroy") Integer destroy);

    @FormUrlEncoded
    @POST("user_overlap_check.php")
    Call<MemberModel> user_overlap_check (@Field("username") String username);
}