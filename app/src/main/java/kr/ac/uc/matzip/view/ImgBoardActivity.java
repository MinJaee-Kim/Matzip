package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import kr.ac.uc.matzip.presenter.ImgBoardPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        dummyBoards.add(new ImgBoardPresenter("http://150.230.136.110/image/9_1_IMG_20210527_075557.jpg"));
        dummyBoards.add(new ImgBoardPresenter("http://150.230.136.110/image/9_1_IMG_20210527_075557.jpg"));
        dummyBoards.add(new ImgBoardPresenter("http://150.230.136.110/image/9_1_IMG_20210527_075557.jpg"));
        dummyBoards.add(new ImgBoardPresenter("http://150.230.136.110/image/9_1_IMG_20210527_075557.jpg"));
        dummyBoards.add(new ImgBoardPresenter("http://150.230.136.110/image/9_1_IMG_20210527_075557.jpg"));

        mImgBoardListFragment.setBoard(dummyBoards);

    }

    private void GetPost() {
        BoardAPI boardAPI = ApiClient.getApiClient().create(BoardAPI.class);
        boardAPI.getPost().enqueue(new Callback<List<BoardModel>>() {
            @Override
            public void onResponse(Call<List<BoardModel>> call, Response<List<BoardModel>> response) {
                List<BoardModel> boardList = response.body();

                Log.d(TAG, "onResponse: " + boardList);

                dummyBoards = new ArrayList<>();

                for(int i = 0; i < boardList.size(); ++i)
                {
                    Log.d(TAG, "onResponse: " + boardList.get(i).getPhoto_uri());
                    dummyBoards.add(new ImgBoardPresenter("http://150.230.131.84/image/81_0_google_PNG102346.png"));
                }

                mImgBoardListFragment.setBoard(dummyBoards);
            }
            @Override
            public void onFailure(Call<List<BoardModel>> call, Throwable t) {

            }
        });
    }
}
