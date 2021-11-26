package kr.ac.uc.matzip.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import kr.ac.uc.matzip.model.ImgBoardModel;

public class ImgBoardPresenter implements ImgBoardModel.View, Parcelable {
    ImgBoardModel.View view;
    ImgBoardPresenter(ImgBoardModel.View view){
        this.view = view;
    }

    private int id;
    private String imageUrl;

    //생성자
    public ImgBoardPresenter(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //게터
    protected ImgBoardPresenter(Parcel in) {
        imageUrl = in.readString();
    }

    public static final Creator<ImgBoardPresenter> CREATOR = new Creator<ImgBoardPresenter>() {
        @Override
        public ImgBoardPresenter createFromParcel(Parcel in) {
            return new ImgBoardPresenter(in);
        }

        @Override
        public ImgBoardPresenter[] newArray(int size) {
            return new ImgBoardPresenter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(imageUrl);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
