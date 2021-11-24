package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardModel {
    //board_id int auto_increment primary key,
    //seq int,
    //bo_title text not null,
    //bo_cont text not null,
    //bo_date datetime,
    //bo_love int,
    //view_count int
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

    @Expose
    @SerializedName("photo_id")
    private int photo_id;

    @Expose
    @SerializedName("photo_name")
    private String photo_name;

    @Expose
    @SerializedName("photo_uri")
    private String photo_uri;

}
