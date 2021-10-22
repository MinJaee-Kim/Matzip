package kr.ac.uc.matzip.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.presenter.BoardPresenter;

public class MainActivity extends AppCompatActivity implements BoardModel.View {
    private BoardPresenter boardPresenter;
    private static final String TAG = "MainActivity";
    Button regBtn, mapBtn, loginBtn, imgBtn, boardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regBtn = findViewById(R.id.regBtn);
        mapBtn = findViewById(R.id.mapBtn);
        loginBtn = findViewById(R.id.imgBtn);
        imgBtn = findViewById(R.id.regBtn);
        boardBtn = findViewById(R.id.boardBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}