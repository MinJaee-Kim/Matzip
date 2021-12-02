package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoModel {
    // GET 메소드를 담아옴
    @Expose
    @SerializedName("documents")
    private List<Document> documents;

    @Expose
    @SerializedName("errorType")
    private String errorType;

    @Expose
    @SerializedName("message")
    private String message;

    @Getter
    @Setter
    public static class Document {
        @Expose
        @SerializedName("address_name")
        private String address_name;

        @Expose
        @SerializedName("category_group_code")
        private String category_group_code;

        @Expose
        @SerializedName("category_group_name")
        private String category_group_name;

        @Expose
        @SerializedName("category_name")
        private String category_name;

        @Expose
        @SerializedName("distance")
        private String distance;

        @Expose
        @SerializedName("id")
        private String id;

        @Expose
        @SerializedName("phone")
        private String phone;

        @Expose
        @SerializedName("place_name")
        private String place_name;

        @Expose
        @SerializedName("place_url")
        private String place_url;

        @Expose
        @SerializedName("road_address_name")
        private String road_address_name;

        @Expose
        @SerializedName("x")
        private String x;

        @Expose
        @SerializedName("y")
        private String y;
    }
}
