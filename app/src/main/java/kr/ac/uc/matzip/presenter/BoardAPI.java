package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.BoardModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BoardAPI {
    @FormUrlEncoded
    @POST("insert_Board.php")
    Call<BoardModel> postData (@Field("bo_title") String bo_title,
                              @Field("bo_cont") String bo_cont);
}
