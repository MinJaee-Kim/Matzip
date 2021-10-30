package kr.ac.uc.matzip.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.presenter.ImgBoardPresenter;

public class ImgBoardActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    ImgBoardListFragment mImgBoardListFragment;
    List<ImgBoardPresenter> dummyBoards;
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
        dummyBoards = new ArrayList<>();
        dummyBoards.add(new ImgBoardPresenter("https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"));
        dummyBoards.add(new ImgBoardPresenter("https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"));
        dummyBoards.add(new ImgBoardPresenter("https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"));
        dummyBoards.add(new ImgBoardPresenter("https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"));
        dummyBoards.add(new ImgBoardPresenter("https://www.google.co.kr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"));

        mImgBoardListFragment.setBoard(dummyBoards);

    }

}
