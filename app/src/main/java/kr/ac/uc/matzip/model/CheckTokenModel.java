package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

        public int getIat() {
            return iat;
        }

        public void setIat(int iat) {
            this.iat = iat;
        }
    }

    public Jwt_payload getJwt_payload() {
        return jwt_payload;
    }

    public void setJwt_payload(Jwt_payload jwt_payload) {
        this.jwt_payload = jwt_payload;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
