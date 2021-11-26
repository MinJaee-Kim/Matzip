package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.CommentModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommentAPI {
    @FormUrlEncoded
    @POST("insert_comment.php")
    Call<CommentModel> postData (@Field("board_id") String board_id,
                                 @Field("co_cont") String co_cont);
}
