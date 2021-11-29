package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoveModel {
//    love_id int auto_increment primary key,
//    seq int,
//    board_id int,
//    is_love char
    @Expose
    @SerializedName("love_id")
    private int love_id;

    @Expose
    @SerializedName("seq")
    private int seq;

    @Expose
    @SerializedName("board_id")
    private int board_id;

    @Expose
    @SerializedName("is_love")
    private String is_love;
}
