package kr.ac.uc.matzip.presenter;

import java.util.List;

import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.model.PhotoModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MemberAPI {
    // 회원가입
    @FormUrlEncoded
    @POST("register.php") // 회원 데이터를 저장하는 php
    Call<MemberModel> regMember (@Field("username") String username,
                                @Field("password") String password,
                                @Field("nickname") String nickname);

    // 회원가입 중복체크
    @FormUrlEncoded
    @POST("user_overlap_check.php") // 회원 데이터가 중복되는지 검사하는 php
    Call<MemberModel> user_overlap_check (@Field("username") String username);

    // 로그인
    @FormUrlEncoded
    @POST("login.php") // 저장된 데이터 값을 비교하고 맞으면 토큰을 생성하는 php
    Call<MemberModel> getLogin(@Field("username") String username
                                ,@Field("is_auto_log") Integer is_auto_log); // Call<응답받을 데이터형> 함수명(서버에 전달할 데이터)

    // 로그인 기록 저장
    @FormUrlEncoded
    @POST("login_history.php") //
    Call<MemberModel> insert_login_history (@Field("user_id") Integer user_id,
                                            @Field("token_value") String token_value);

    //토큰 삭제
    @FormUrlEncoded
    @POST("token_logout.php") // 토큰값을 사용할 수 없게 업데이트 하는 php
    Call<MemberModel> logOut(@Field("destroy") Integer destroy);

    // 로그아웃 기록 저장
    @FormUrlEncoded
    @POST("logout_history.php") //
    Call<MemberModel> update_logout_history (@Field("user_id") Integer user_id,
                                            @Field("token_value") String token_value);

    // 회원정보 출력
    @POST("select_profile.php") //
    Call<List<MemberModel>> getProfile ();

    // 프로필 사진 저장
    @Multipart
    @POST("upload_user_profile.php") // 사진 파일을 서버에 저장하는 php
    Call<MemberModel> uploadProfile(@Part MultipartBody.Part uploaded_file);

    // 프로필 사진 uri 업데이트
    @FormUrlEncoded
    @POST("update_profile.php") // 회원 데이터가 중복되는지 검사하는 php
    Call<MemberModel> user_profile_update (@Field("user_photo_uri") String user_photo_uri);
}