package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Co_CommentListModel {
    @Expose
    @SerializedName("co_comment_id")
    private int co_comment_id;

    @Expose
    @SerializedName("comment_id")
    private int comment_id;

    @Expose
    @SerializedName("user_id")
    private int user_id;

    @Expose
    @SerializedName("co_co_cont")
    private String co_co_cont;

    @Expose
    @SerializedName("co_co_love")
    private int co_co_love;

    @Expose
    @SerializedName("co_co_date")
    private String co_co_date;

    @Expose
    @SerializedName("nickname")
    private String nickname;

    @Expose
    @SerializedName("user_photo_uri")
    private String user_photo_uri;

    @Expose
    @SerializedName("success")
    private String success;
}
