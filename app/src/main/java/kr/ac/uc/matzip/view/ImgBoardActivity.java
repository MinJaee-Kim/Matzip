package kr.ac.uc.matzip.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import kr.ac.uc.matzip.R;

public class ImgBoardActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    ImgBoardListFragment mImgBoardListFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgboard_activity);

        FragmentManager manager = getSupportFragmentManager();
        if(savedInstanceState == null){
            FragmentTransaction transaction = manager.beginTransaction();
            mImgBoardListFragment = new ImgBoardListFragment();
            transaction.add(R.id.imgboard_activity_frame, mImgBoardListFragment, TAG_LIST_FRAGMENT);
            transaction.commit();
        }else{
            mImgBoardListFragment = (ImgBoardListFragment) manager.findFragmentByTag(TAG_LIST_FRAGMENT);
        }
    }
}
