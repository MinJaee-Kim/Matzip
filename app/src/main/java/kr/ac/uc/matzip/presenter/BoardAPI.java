package kr.ac.uc.matzip.presenter;

import java.util.List;

import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.model.BoardModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BoardAPI {
    @FormUrlEncoded
    @POST("insert_board.php") // 게시판을 저장하는 php
    Call<BoardModel> postData (@Field("bo_title") String bo_title,
                                @Field("bo_cont") String bo_cont);

    @FormUrlEncoded
    @POST("delete_board.php") // 게시판을 삭제하는 php
    Call<BoardModel> deletePost (@Field("board_id") Integer board_id);

    @POST("select_board_list.php") // 게시판에 대한 모든 데이터를 출력하는 php
    Call<List<BoardListModel>> getBoardList();

    @POST("select_user_board_list.php") // 게시판에 대한 모든 데이터를 출력하는 php
    Call<List<BoardListModel>> getUserBoardList();

    @FormUrlEncoded
    @POST("select_user_board.php") // 특정유저의 게시판에대한 데이터를 출력하는 php
    Call<List<BoardListModel>> getUserBoard(@Field("user_id") Integer user_id);

    @FormUrlEncoded
    @POST("select_board_id.php") // 게시판에 대한 모든 데이터를 출력하는 php
    Call<List<BoardListModel>> getBoard(@Field("board_id") Integer board_id);
}
