package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import kr.ac.uc.matzip.model.CheckTokenModel;
import kr.ac.uc.matzip.model.TokenModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.TokenAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveSharedPreference {
    public static final String PREFERENCES_NAME = "rebuild_preference";
    private Context mContext;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;
    private static SaveSharedPreference instance;

    private static final String DEFAULT_VALUE_STRING = "";
    private static final boolean DEFAULT_VALUE_BOOLEAN = false;
    private static final int DEFAULT_VALUE_INT = -1;


    public static synchronized SaveSharedPreference init(Context context){
        if(instance == null)
            instance = new SaveSharedPreference(context);
        return instance;
    }

    private SaveSharedPreference(Context context) {
        mContext = context;
        prefs = mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    //TODO == [String 값 저장] ==
    public static void setString(String key, String value) {
        Log.d(TAG, "setString: " + key + ": " + value);
        editor.putString(key, value);
        editor.commit();
        editor.apply();
    }

    //TODO == [boolean 값 저장] ==
    public static void setBoolean(String key, boolean value) {
        Log.d(TAG, "setBoolean: " + key + ": " + value);
        editor.putBoolean(key, value);
        editor.commit();
        editor.apply();
    }

    //TODO == [int 값 저장] ==
    public static void setInt(String key, int value) {
        Log.d(TAG, "setInt: " + key + ": " + value);
        editor.putInt(key, value);
        editor.commit();
        editor.apply();
    }

    //TODO == [String 값 호출] ==
    public static String getString(String key) {
        String value = prefs.getString(key, DEFAULT_VALUE_STRING);
        return value;
    }

    //TODO == [boolean 값 호출] ==
    public static boolean getBoolean(String key) {
        boolean value = prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN);
        return value;
    }

    //TODO == [int 값 호출] ==
    public static int getInt(String key) {
        int value = prefs.getInt(key, DEFAULT_VALUE_INT);
        return value;
    }

    public static boolean checkLogin(Context context) {
        if (SaveSharedPreference.getString("token") == "") {
            Log.d(TAG, "checkLogin: " + SaveSharedPreference.getString("token"));
            return false;
        } else {
            TokenAPI tokenAPI = ApiClient.getApiClient().create(TokenAPI.class);
            tokenAPI.check_Token(SaveSharedPreference.getString("token")).enqueue(new Callback<CheckTokenModel>() {
                @Override
                public void onResponse(Call<CheckTokenModel> call, Response<CheckTokenModel> response) {
                    CheckTokenModel checktokenModel = response.body();

                    Log.d(TAG, "onResponse: 대한민국" + response.raw());
                    Log.d(TAG, "onResponse: 독도" + checktokenModel.getCode());

                    if (checktokenModel.getCode() == 200) {
                        Log.d(TAG, "onResponse: " + checktokenModel.getStatus());
                        setInt("id", checktokenModel.getJwt_payload().getId());
                        setInt("loginCode", checktokenModel.getCode());
                    }else
                    {
                        clear();
                    }
                }

                @Override
                public void onFailure(Call<CheckTokenModel> call, Throwable t) {
                    Log.d(TAG, "checkLogin: " + t.getMessage());
                }
            });

            if (getInt("loginCode") == 200) {
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
                setString("token", res.getToken());
            }

            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                Log.d(TAG, "GetToken: " + t.getMessage());
            }
        });
    }

    //TODO == [특정 key 삭제] ==
    public static void removeKey(String key) {
        editor.remove(key);
        editor.commit();
        editor.apply();
    }

    //TODO == [전체 key 삭제] ==
    public static void clear() {
        editor.clear();
        editor.commit();
        editor.apply();
    }
}
