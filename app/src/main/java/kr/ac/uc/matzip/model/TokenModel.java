package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

    @Expose
    @SerializedName("result")
    Result result;

    public static class Result {
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
            private int password;

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

            public int getPassword() {
                return password;
            }

            public void setPassword(int password) {
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
