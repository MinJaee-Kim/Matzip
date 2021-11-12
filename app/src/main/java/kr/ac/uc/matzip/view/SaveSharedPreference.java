package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.model.TokenModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.TokenAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveSharedPreference {
    static final String PREF_USER_TOKEN = "token";
    static final String PREF_USER_ID = "id";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserToken(Context ctx, String getToken) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_TOKEN, getToken);
        editor.commit();
    }

    public static void setUserId(Context ctx, Integer getId) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_USER_ID, getId);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getUserToken(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_TOKEN, "");
    }

    public static int getUserStatus(Context ctx) {
        return getSharedPreferences(ctx).getInt(PREF_USER_ID, 0);
    }

    public static boolean checkLogin(Context ctx) {
        if(SaveSharedPreference.getUserToken(ctx) == "")
        {
            Log.d(TAG, "checkLogin: " + SaveSharedPreference.getUserToken(ctx));
            return false;
        }
        else {
            final int[] code = {0};
            TokenAPI tokenAPI = ApiClient.getApiClient().create(TokenAPI.class);
            tokenAPI.check_Token(SaveSharedPreference.getUserToken(ctx)).enqueue(new Callback<TokenModel>() {
                @Override
                public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                    List<TokenModel.TokRul> list = new ArrayList(response.body().getResult());

                    if (list.get(0).getCode() == 200) {
                        Log.d(TAG, "onResponse: " + list.get(0).getStatus());
                    }
                    code[0] = list.get(0).getCode();
                }

                @Override
                public void onFailure(Call<TokenModel> call, Throwable t) {

                }
            });

            if (code[0] == 200) {
                return true;
            } else {
                return false;
            }
        }
    }


    // 로그아웃
    public static void clearUserToken(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }

}
