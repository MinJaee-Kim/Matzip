package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.LoveModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoveAPI {
    @FormUrlEncoded
    @POST("love_board.php") // 좋아요 확인후 한번누르면 좋아요를 만들고 두번 두르면 취소됨
    Call<LoveModel> love_post (@Field("board_id") Integer board_id);

    @FormUrlEncoded
    @POST("love_check.php") // 보드 좋아요 버튼 판단
    Call<LoveModel> love_check (@Field("board_id") Integer board_id);

    @FormUrlEncoded
    @POST("select_love_user.php") // 보드 좋아요 유저 판단
    Call<LoveModel> love_user (@Field("board_id") Integer board_id);

    @FormUrlEncoded
    @POST("select_love_count.php") // 보드 좋아요 개수 판단
    Call<LoveModel> love_count (@Field("board_id") Integer board_id);
}
