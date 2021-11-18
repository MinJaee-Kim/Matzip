package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckTokenModel {
    @Expose
    @SerializedName("code")
    private int code;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("jwt_payload")
    Jwt_payload jwt_payload;

    @Getter
    @Setter
    public class Jwt_payload {
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
        @SerializedName("iat")
        private int iat;
    }
}
