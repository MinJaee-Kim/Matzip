package kr.ac.uc.matzip.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.model.ImgBoardModel;

public class ImgBoardPresenter implements ImgBoardModel.View, Parcelable {
    ImgBoardModel.View view;
    ImgBoardPresenter(ImgBoardModel.View view){
        this.view = view;
    }

    private int id;
    private String name;
    private String description;
    private String imageUrl;

    //생성자
    public ImgBoardPresenter(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    //게터
    protected ImgBoardPresenter(Parcel in) {
        name = in.readString();
        description = in.readString();
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageUrl);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
