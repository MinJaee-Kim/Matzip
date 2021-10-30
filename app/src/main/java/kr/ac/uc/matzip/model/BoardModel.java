package kr.ac.uc.matzip.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoardModel {
    private SharedPreferences app_prefs;
    private Context context;

    public BoardModel(Context context)
    {
        app_prefs = context.getSharedPreferences("shared", 0);
        this.context = context;
    }

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("bo_title")
    private String bo_title;

    @SerializedName("bo_cont")
    private String bo_cont;

    @SerializedName("nowdate")
    private String nowdate; // mysql에서 datetime은 문자열로 전송된다.

    @SerializedName("bo_love")
    private int bo_love;

    @SerializedName("viewcount")
    private int viewcount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return app_prefs.getString(username, "");
    }

    public void setUsername(String username) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(username, username);
        edit.apply();
    }

    public String getTitle()
    {
        return app_prefs.getString(bo_title, "");
    }

    public void putTitle(String bo_title)
    {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(bo_title, bo_title);
        edit.apply();
    }

    public String getCont() {
        return app_prefs.getString(bo_cont, "");
    }

    public void putCont(String bo_cont) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(bo_cont, bo_cont);
        edit.apply();
    }

    public String getNowdate() {
        return app_prefs.getString(nowdate, "");
    }

    public void setNowdate(String nowdate) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(nowdate, nowdate);
        edit.apply();
    }

    public int getLove() {
        return bo_love;
    }

    public void setLove(int bo_love) {
        this.bo_love = bo_love;
    }

    public int getViewcount() {
        return viewcount;
    }

    public void setViewcount(int viewcount) {
        this.viewcount = viewcount;
    }
}
