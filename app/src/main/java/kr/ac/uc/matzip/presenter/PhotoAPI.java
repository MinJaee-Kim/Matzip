package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.PhotoModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PhotoAPI {
    @Multipart
    @POST("insert_photo.php")
    Call<PhotoModel> postData();

    @Multipart
    @POST("upload_file.php")
    Call<PhotoModel> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);
}
