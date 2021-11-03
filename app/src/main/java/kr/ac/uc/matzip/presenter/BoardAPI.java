package kr.ac.uc.matzip.presenter;

import java.util.List;

import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.model.PhotoModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BoardAPI {
    @FormUrlEncoded
    @POST("insert_board.php")
    Call<BoardModel> postData (@Field("bo_title") String bo_title,
                           @Field("bo_cont") String bo_cont);

    @Multipart
    @POST("insert_photo.php")
    Call<PhotoModel> postData();

    @Multipart
    @POST("upload_file.php")
    Call<String> request(@Part MultipartBody.Part file);

    @POST("select_board.php") // @전송방식(데이터를 전송할 서버 파일명)
    Call<List<BoardModel>> getPost(); // Call<응답받을 데이터형> 함수명(서버에 전달할 데이터)
}
