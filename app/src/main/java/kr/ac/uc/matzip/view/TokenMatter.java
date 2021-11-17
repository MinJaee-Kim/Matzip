package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.model.CheckTokenModel;
import kr.ac.uc.matzip.model.TokenModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.TokenAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenMatter {
//    private void AutoLogin(Context context){
//
//        if(SaveSharedPreference.getUserToken(context).length() == 0 && context == null) {
//            // call Login Activity
//            Intent intent = new Intent(context, LoginActivity.class);
//            context.startActivity(intent);
//        } else {
//            TokenAPI tokenAPI = ApiClient.getApiClient().create(TokenAPI.class);
//            tokenAPI.check_Token(saveSharedPreference.getUserToken(context)).enqueue(new Callback<TokenModel>() {
//                @Override
//                public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
//                    List<TokenModel.TokRul> list = new ArrayList(response.body().getResult());
//
//                    if(list.get(0).getCode() == 200){
//                        Log.d(TAG, "onResponse: " + list.get(0).getStatus());
//                    }else{
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        context.startActivity(intent);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TokenModel> call, Throwable t) {
//
//                }
//            });
//        }
//    }

    public static boolean checkLogin(Context context) {
        if (SaveSharedPreference.getString("token") == "") {
            Log.d(TAG, "checkLogin: " + SaveSharedPreference.getString("token"));
            return false;
        } else {
            TokenAPI tokenAPI = ApiClient.getApiClient().create(TokenAPI.class);
            tokenAPI.check_Token().enqueue(new Callback<CheckTokenModel>() {
                @Override
                public void onResponse(Call<CheckTokenModel> call, Response<CheckTokenModel> response) {
                    CheckTokenModel checktokenModel = response.body();

                    Log.d(TAG, "onResponse: 대한민국" + response.body().getMessage());
                    Log.d(TAG, "onResponse: 독도" + checktokenModel.getCode());

                    if (checktokenModel.getCode() == 200) {
                        Log.d(TAG, "onResponse: " + checktokenModel.getStatus());
                        SaveSharedPreference.setInt("id", checktokenModel.getJwt_payload().getId());
                        SaveSharedPreference.setInt("loginCode", checktokenModel.getCode());
                    }else
                    {
                        SaveSharedPreference.clear();
                    }
                }

                @Override
                public void onFailure(Call<CheckTokenModel> call, Throwable t) {
                    Log.d(TAG, "checkLogin: " + t.getMessage());
                }
            });

            if (SaveSharedPreference.getInt("loginCode") == 200) {
                Log.d(TAG, "checkLogin: 성공");
                return true;
            } else {
                Log.d(TAG, "checkLogin: 실패");
                return false;
            }
        }
    }

    public static void GetToken(String id, Integer autolog){
        TokenAPI tokenAPI = ApiClient.getApiClient().create(TokenAPI.class);
        tokenAPI.getToken(id, autolog).enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                TokenModel res = response.body();
                Log.d(TAG, "GetToken: " + res.getToken());
                SaveSharedPreference.setString("token", res.getToken());
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Log.d(TAG, "GetToken: " + t.getMessage());
            }
        });
    }
}
