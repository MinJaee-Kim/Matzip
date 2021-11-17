package kr.ac.uc.matzip.presenter;

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
    @POST("insert_photo.php")
    Call<PhotoModel> postData();

    @Multipart
    @POST("upload_file.php")
    Call<String> uploadPhoto(@Part MultipartBody.Part uploaded_file,
                             @Part("board_id") Integer board_id,
                             @Part("photo_num") Integer photo_num);
}

