package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.KakaoModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoAPI {
    @GET("v2/local/search/keyword.json")
    Call<KakaoModel> searchAddressList(@Header("Authorization") String apikey,
                                       @Query("query") String query);
}
