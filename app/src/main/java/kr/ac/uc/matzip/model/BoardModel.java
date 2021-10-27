package kr.ac.uc.matzip.model;

import com.google.gson.annotations.SerializedName;

public class BoardModel {
    @SerializedName("id")
    int id;

    @SerializedName("username")
    int username;

    @SerializedName("bo_title")
    String bo_title;

    @SerializedName("bo_cont")
    String bo_cont;


    @SerializedName("nowdate")
    String nowdate; // mysql에서 datetime은 문자열로 전송된다.

    @SerializedName("bo_love")
    int bo_love;

    @SerializedName("viewcount")
    int viewcount;


    public Integer getId() {
        return id;
    }

    public String getBo_title() {
        return bo_title;
    }

    public String getBo_cont() {
        return bo_cont;
    }

    public String getNowdate() {
        return nowdate;
    }

    public int getBo_love() {
        return bo_love;
    }

    public Integer getViewcount() {
        return viewcount;
    }
}
