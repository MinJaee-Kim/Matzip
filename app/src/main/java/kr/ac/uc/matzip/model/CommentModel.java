package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentModel {
    @Expose
    @SerializedName("board_id")
    private int board_id;

    @Expose
    @SerializedName("seq")
    private int seq;

    @Expose
    @SerializedName("bo_title")
    private String bo_title;

    @Expose
    @SerializedName("bo_cont")
    private String bo_cont;

    @Expose
    @SerializedName("bo_date")
    private String bo_date; // mysql에서 dateTime은 문자열로 전송된다.

    @Expose
    @SerializedName("bo_love")
    private int bo_love;

    @Expose
    @SerializedName("view_count")
    private int view_count;

    @Expose
    @SerializedName("success")
    private String success;
}
