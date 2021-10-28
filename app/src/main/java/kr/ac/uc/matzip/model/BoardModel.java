package kr.ac.uc.matzip.model;

import com.google.gson.annotations.SerializedName;

public class BoardModel {
    @SerializedName("id")
    int id;

    @SerializedName("username")
    String username;

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

    public String getUsername() {
        return username;
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

    public void setId(int id) { this.id = id; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBo_title(String bo_title) {
        this.bo_title = bo_title;
    }

    public void setBo_cont(String bo_cont) {
        this.bo_cont = bo_cont;
    }

    public void setNowdate(String nowdate) {
        this.nowdate = nowdate;
    }

    public void setBo_love(int bo_love) {
        this.bo_love = bo_love;
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }
}
