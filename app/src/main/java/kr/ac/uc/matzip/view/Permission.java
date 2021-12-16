package kr.ac.uc.matzip.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Permission extends AppCompatActivity{

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSIONS_REQUEST_CODE);
                    return false;
                }
            }
        }
        return true;
    }
}
