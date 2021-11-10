package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getIsusing() {
        return isusing;
    }

    public void setIsusing(String isusing) {
        this.isusing = isusing;
    }

    public int getCreate_date() {
        return create_date;
    }

    public void setCreate_date(int create_date) {
        this.create_date = create_date;
    }

    public int getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(int expire_date) {
        this.expire_date = expire_date;
    }
}
