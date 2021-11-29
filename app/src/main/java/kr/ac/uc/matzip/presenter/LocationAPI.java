package kr.ac.uc.matzip.presenter;

import java.util.List;

import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.model.LocationModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LocationAPI {
    @FormUrlEncoded
    @POST("insert_location.php") // 위치를 저장하는 php
    Call<LocationModel> postData (@Field("board_id") Integer board_id,
                                  @Field("latitude") Double latitude,
                                  @Field("longitude") Double longitude);

    @POST("location_board.php") // 위치값을 출력하는 php
    Call<List<LocationModel>> getLocationBoard();

}
