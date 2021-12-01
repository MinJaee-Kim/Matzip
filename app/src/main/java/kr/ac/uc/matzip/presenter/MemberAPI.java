package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.MemberModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MemberAPI {
    @FormUrlEncoded
    @POST("register.php") // 회원 데이터를 저장하는 php
    Call<MemberModel> regMember (@Field("username") String username,
                                @Field("password") String password,
                                @Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("login.php") // 저장된 데이터 값을 비교하고 맞으면 토큰을 생성하는 php
    Call<MemberModel> getLogin(@Field("username") String username
                                ,@Field("is_auto_log") Integer is_auto_log); // Call<응답받을 데이터형> 함수명(서버에 전달할 데이터)

    @FormUrlEncoded
    @POST("token_logout.php") // 토큰값을 사용할 수 없게 업데이트 하는 php
    Call<MemberModel> logOut(@Field("destroy") Integer destroy);

    @FormUrlEncoded
    @POST("user_overlap_check.php") // 회원 데이터가 중복되는지 검사하는 php
    Call<MemberModel> user_overlap_check (@Field("username") String username);

    @FormUrlEncoded
    @POST("login_history.php") //
    Call<MemberModel> insert_login_history (@Field("user_id") Integer user_id,
                                            @Field("token_value") String token_value);

    @FormUrlEncoded
    @POST("logout_history.php") //
    Call<MemberModel> update_logout_history (@Field("user_id") Integer user_id,
                                            @Field("token_value") String token_value);
}