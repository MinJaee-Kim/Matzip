package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBo_title() {
        return bo_title;
    }

    public void setBo_title(String bo_title) {
        this.bo_title = bo_title;
    }

    public String getBo_cont() {
        return bo_cont;
    }

    public void setBo_cont(String bo_cont) {
        this.bo_cont = bo_cont;
    }

    public String getNowdate() {
        return nowdate;
    }

    public void setNowdate(String nowdate) {
        this.nowdate = nowdate;
    }

    public int getBo_love() {
        return bo_love;
    }

    public void setBo_love(int bo_love) {
        this.bo_love = bo_love;
    }

    public int getViewcount() {
        return viewcount;
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }

    public int getBo_id() {
        return bo_id;
    }

    public void setBo_id(int bo_id) {
        this.bo_id = bo_id;
    }
}
