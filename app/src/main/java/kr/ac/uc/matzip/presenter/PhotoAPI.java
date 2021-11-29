package kr.ac.uc.matzip.presenter;

import java.util.List;

import kr.ac.uc.matzip.model.PhotoModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PhotoAPI {
    @Multipart
    @POST("upload_file.php") // 사진 파일을 서버에 저장하는 php
    Call<PhotoModel> uploadPhoto(@Part MultipartBody.Part uploaded_file,
                                 @Part("index") Integer index,
                                 @Part("board_id") Integer photo_uri);

    @FormUrlEncoded
    @POST("upload_fileDB.php") // 저장한 파일의 정보를 저장하는 php
    Call<PhotoModel> uploadDB(@Field("board_id") Integer board_id,
                             @Field("photo_uri") String photo_uri,
                              @Field("photo_index") Integer photo_index);

    @FormUrlEncoded
    @POST("select_photo.php") // 저장된 사진 정보를 출력하는 php
    Call<List<PhotoModel>> select_photo(@Field("board_id") Integer board_id);
}

