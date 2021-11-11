package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.model.TokenModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.TokenAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenMatter {
    SaveSharedPreference saveSharedPreference = new SaveSharedPreference();

    private void AutoLogin(Context context){

        if(SaveSharedPreference.getUserToken(context).length() == 0 && context == null) {
            // call Login Activity
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else {
            TokenAPI tokenAPI = ApiClient.getApiClient().create(TokenAPI.class);
            tokenAPI.check_Token(saveSharedPreference.getUserToken(context)).enqueue(new Callback<TokenModel>() {
                @Override
                public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                    List<TokenModel.TokRul> list = new ArrayList(response.body().getResult());

                    Log.d(TAG, "onResponse: " + list.get(0).getCode());
                }

                @Override
                public void onFailure(Call<TokenModel> call, Throwable t) {

                }
            });
        }
    }

    protected void GetToken(Context context, String id, Boolean autolog){
        TokenAPI tokenAPI = ApiClient.getApiClient().create(TokenAPI.class);
        tokenAPI.getToken(id, autolog).enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                saveSharedPreference.setUserToken(context, response.body().getToken());
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {

            }
        });
    }
}
