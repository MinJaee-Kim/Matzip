package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.BoardModel;
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
    Call<BoardModel> postData();

    @Multipart
    @POST("upload_file.php")
    Call<BoardModel> uploadPhoto(@Part MultipartBody.Part uploaded_file,
                                 @Part("index") Integer index,
                                 @Part("board_id") Integer photo_uri);

    @FormUrlEncoded
    @POST("upload_fileDB.php")
    Call<BoardModel> uploadDB(@Field("board_id") Integer board_id,
                             @Field("photo_uri") String photo_uri,
                              @Field("photo_index") Integer photo_index);
}

