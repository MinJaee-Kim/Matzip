package kr.ac.uc.matzip.presenter;

import java.util.List;

import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.model.CommentListModel;
import kr.ac.uc.matzip.model.CommentModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommentAPI {
    @FormUrlEncoded
    @POST("insert_comment.php")
    Call<CommentModel> postComment (@Field("board_id") Integer board_id,
                                    @Field("co_cont") String co_cont);

    @FormUrlEncoded
    @POST("select_comment.php")
    Call<List<CommentListModel>> getCommentList(@Field("board_id") Integer board_id);
}
