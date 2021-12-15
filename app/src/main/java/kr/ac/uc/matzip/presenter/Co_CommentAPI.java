package kr.ac.uc.matzip.presenter;

import java.util.List;

import kr.ac.uc.matzip.model.Co_CommentListModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Co_CommentAPI {

    @FormUrlEncoded
    @POST("insert_co_comment.php") // 댓글을 저장하는 php
    Call<Co_CommentListModel> postCo_Comment (@Field("comment_id") Integer comment_id,
                                                @Field("co_co_cont") String co_co_cont);

    @FormUrlEncoded
    @POST("select_co_comment.php") // 댓글 출력하는 php
    Call<List<Co_CommentListModel>> getCo_CommentList(@Field("comment_id") Integer comment_id);
}
