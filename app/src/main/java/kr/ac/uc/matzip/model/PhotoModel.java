package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoModel {
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("bo_id")
    private int bo_id;

    @Expose
    @SerializedName("photo_name")
    private String photo_name;

    @Expose
    @SerializedName("photo_uri")
    private String photo_uri;

    @Expose
    @SerializedName("uploaded_file")
    private String uploaded_file;

    @Expose
    @SerializedName("success")
    private String success;
}
