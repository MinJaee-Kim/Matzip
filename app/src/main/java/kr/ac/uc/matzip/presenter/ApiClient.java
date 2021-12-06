package kr.ac.uc.matzip.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import kr.ac.uc.matzip.view.SaveSharedPreference;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
private static final String BASE_URL = "http://150.230.136.110/php/";
private static final String KAKAO_URL = "https://dapi.kakao.com/";
private static Retrofit retrofit, retrofit2, kakaoretrofit;
private static OkHttpClient client;
private static Gson gson;


    public static Retrofit getApiClient()
    {
        if(gson == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
        }

        client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("token", SaveSharedPreference.getString("token"))
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    }).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public static void deleteApiClient()
    {
        client = null;
        gson = null;
        retrofit = null;
    }

    public static Retrofit getNoHeaderApiClient() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
        }

        if (retrofit2 == null) {
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit2;
    }

    public static Retrofit kakaoSearchApiClient()
    {
        if(gson == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
        }

        if(kakaoretrofit == null) {
            kakaoretrofit = new Retrofit.Builder()
                    .baseUrl(KAKAO_URL)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return kakaoretrofit;
    }
}
