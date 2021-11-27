package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentModel {
    @Expose
    @SerializedName("comment_id")
    private int comment_id;

    @Expose
    @SerializedName("board_id")
    private int board_id;

    @Expose
    @SerializedName("seq")
    private int seq;

    @Expose
    @SerializedName("co_cont")
    private String co_cont;

    @Expose
    @SerializedName("co_love")
    private int co_love;

    @Expose
    @SerializedName("co_date")
    private String co_date; // mysql에서 dateTime은 문자열로 전송된다.

    @Expose
    @SerializedName("success")
    private String success;
}
