package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenModel {
    // to_id int auto_increment primary key
    // token text not null
    // seq int
    // is_using char
    // create_date int
    // expire_date int
    @Expose
    @SerializedName("token_id")
    private int token_id;

    @Expose
    @SerializedName("token_value")
    private String token_value;

    @Expose
    @SerializedName("success")
    private String success;

    @Expose
    @SerializedName("seq")
    private int seq;

    @Expose
    @SerializedName("is_using")
    private String is_using;

    @Expose
    @SerializedName("create_date")
    private int create_date;

    @Expose
    @SerializedName("expire_date")
    private int expire_date;
}
