package kr.ac.uc.matzip.presenter;

import java.util.List;

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

    @POST("select_Board.php") // @전송방식(데이터를 전송할 서버 파일명)
    Call<List<MemberModel>> getMember(); // Call<응답받을 데이터형> 함수명(서버에 전달할 데이터)
}
