package kr.ac.uc.matzip.model;

import com.google.gson.annotations.SerializedName;

public class MemberModel {
    //id int auto_increment primary key,
    //username varchar(50) unique key not null,
    //password varchar(100) not null,
    //nickname varchar(50),
    //user_photo_url varchar(100),
    //status_message text,
    //authority varchar(50),
    //enabled int
    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("user_photo_utl")
    private String user_photo_utl;

    @SerializedName("status_message")
    private String status_message;

    @SerializedName("authority")
    private String authority;

    @SerializedName("enabled")
    private int enabled;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_photo_utl() {
        return user_photo_utl;
    }

    public void setUser_photo_utl(String user_photo_utl) {
        this.user_photo_utl = user_photo_utl;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public MemberModel(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
