package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoModel {
//    photo_id int auto_increment primary key,
//    board_id int,
//    photo_url text,
//    photo_index int
    @Expose
    @SerializedName("board_id")
    private int board_id;

    @Expose
    @SerializedName("photo_id")
    private int photo_id;

    @Expose
    @SerializedName("photo_name")
    private String photo_name;

    @Expose
    @SerializedName("photo_uri")
    private String photo_uri;

    @Expose
    @SerializedName("success")
    private String success;
}
