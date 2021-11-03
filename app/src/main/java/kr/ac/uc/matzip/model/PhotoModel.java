package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBo_id() {
        return bo_id;
    }

    public void setBo_id(int bo_id) {
        this.bo_id = bo_id;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }

    public String getUploaded_file() {
        return uploaded_file;
    }

    public void setUploaded_file(String uploaded_file) {
        this.uploaded_file = uploaded_file;
    }
}
