package kr.ac.uc.matzip.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationModel {
    @Expose
    @SerializedName("board_id")
    private int board_id;

    @Expose
    @SerializedName("location_id")
    private int location_id;

    @Expose
    @SerializedName("latitude")
    private Double latitude;

    @Expose
    @SerializedName("longitude")
    private Double longitude;

    @Expose
    @SerializedName("success")
    private String success;
}
