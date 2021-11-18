package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
