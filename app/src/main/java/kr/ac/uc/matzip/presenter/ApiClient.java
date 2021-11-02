package kr.ac.uc.matzip.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://150.230.131.84/php/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient()
    {
        if (retrofit == null)
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
