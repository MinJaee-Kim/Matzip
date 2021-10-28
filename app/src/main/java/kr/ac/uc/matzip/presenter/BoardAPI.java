package kr.ac.uc.matzip.presenter;

import java.util.HashMap;
import java.util.List;

import kr.ac.uc.matzip.model.BoardModel;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BoardAPI {
    @FormUrlEncoded
    @POST("insert_Board.php")
    Call<BoardModel> postData (@FieldMap HashMap<String, Object> param);

    @POST("select_Board.php") // @전송방식(데이터를 전송할 서버 파일명)
    Call<List<BoardModel>> getPost(); // Call<응답받을 데이터형> 함수명(서버에 전달할 데이터)
}
