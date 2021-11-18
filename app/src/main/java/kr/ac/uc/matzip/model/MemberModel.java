package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
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
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("nickname")
    private String nickname;

    @Expose
    @SerializedName("success")
    private Boolean isSuccess;

    @Expose
    @SerializedName("user_photo_utl")
    private String user_photo_utl;

    @Expose
    @SerializedName("status_message")
    private String status_message;

    @Expose
    @SerializedName("authority")
    private String authority;

    @Expose
    @SerializedName("enabled")
    private int enabled;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Expose
    @SerializedName("token")
    private String token;

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

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
