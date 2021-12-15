package kr.ac.uc.matzip.presenter;

import java.util.List;

import kr.ac.uc.matzip.model.CommentListModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommentAPI {

    @FormUrlEncoded
    @POST("insert_comment.php") // 댓글을 저장하는 php
    Call<CommentListModel> postComment (@Field("board_id") Integer board_id,
                                    @Field("co_cont") String co_cont);

    @FormUrlEncoded
    @POST("select_comment.php") // 댓글 출력하는 php
    Call<List<CommentListModel>> getCommentList(@Field("board_id") Integer board_id);

    @FormUrlEncoded
    @POST("select_comment_id.php") // 댓글 id로 출력하는 php
    Call<List<CommentListModel>> getCommentId(@Field("comment_id") Integer board_id);
}
