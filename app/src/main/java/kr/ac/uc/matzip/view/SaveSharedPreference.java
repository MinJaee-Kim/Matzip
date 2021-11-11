package kr.ac.uc.matzip.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_TOKEN = "token";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserName(Context ctx, String getToken) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_TOKEN, getToken);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getUserToken(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_TOKEN, "");
    }


    // 로그아웃
    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }

}
