package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardModel {
    //id int auto_increment primary key,
    //username varchar(50),
    //bo_title text not null,
    //bo_cont text not null,
    //nowdate datetime,
    //bo_love int,
    //viewcount int
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("bo_id")
    private int bo_id;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("bo_title")
    private String bo_title;

    @Expose
    @SerializedName("bo_cont")
    private String bo_cont;

    @Expose
    @SerializedName("nowdate")
    private String nowdate; // mysql에서 datetime은 문자열로 전송된다.

    @Expose
    @SerializedName("bo_love")
    private int bo_love;

    @Expose
    @SerializedName("viewcount")
    private int viewcount;
}
