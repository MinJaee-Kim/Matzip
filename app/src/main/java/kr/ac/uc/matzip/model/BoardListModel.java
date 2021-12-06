package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardListModel {
    // 보드 리스트 데이터를 담아주는 모델
    @Expose
    @SerializedName("board_id")
    private int board_id;

    @Expose
    @SerializedName("user_photo_uri")
    private String user_photo_uri;

    @Expose
    @SerializedName("photo_uri")
    private String photo_uri;

    @Expose
    @SerializedName("nickname")
    private String nickname;

    @Expose
    @SerializedName("bo_title")
    private String bo_title;

    @Expose
    @SerializedName("bo_cont")
    private String bo_cont;

    @Expose
    @SerializedName("bo_love")
    private int bo_love;
}
