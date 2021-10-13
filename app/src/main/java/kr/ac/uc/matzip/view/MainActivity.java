package kr.ac.uc.matzip.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.presenter.BoardPresenter;

public class MainActivity extends AppCompatActivity implements BoardModel {
    Button testBtn;
    private BoardPresenter boardPresenter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testBtn = findViewById(R.id.testBtn);
    }

    @Override
    public void testLog(){
        Log.d(TAG, "testLog: MVP로 로그를 찍어보자");
    }

}