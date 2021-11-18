package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenModel {
    //            to_id int auto_increment primary key
//            token text not null
//            seq int
//            isusing char
//            create_date int
//            expire_date int
    @Expose
    @SerializedName("to_id")
    private int to_id;

    @Expose
    @SerializedName("token")
    private String token;

    @Expose
    @SerializedName("success")
    private String success;

    @Expose
    @SerializedName("seq")
    private int seq;

    @Expose
    @SerializedName("isusing")
    private String isusing;

    @Expose
    @SerializedName("create_date")
    private int create_date;

    @Expose
    @SerializedName("expire_date")
    private int expire_date;
}
