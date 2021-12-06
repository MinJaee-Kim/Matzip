package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentListModel {
    // 댓글 리스트 데이터를 담아주는 모델
    @Expose
    @SerializedName("comment_id")
    private int comment_id;

    @Expose
    @SerializedName("board_id")
    private int board_id;

    @Expose
    @SerializedName("user_id")
    private int user_id;

    @Expose
    @SerializedName("co_cont")
    private String co_cont;

    @Expose
    @SerializedName("co_love")
    private int co_love;

    @Expose
    @SerializedName("co_date")
    private String co_date;

    @Expose
    @SerializedName("nickname")
    private String nickname;

    @Expose
    @SerializedName("user_photo_uri")
    private String user_photo_uri;
}
