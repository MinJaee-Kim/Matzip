package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardListActivity extends AppCompatActivity {

    private ArrayList<BoardListModel> arrayList;
    private BoardListAdapter mBoardListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_layout);

        mRecyclerView = (RecyclerView)findViewById(R.id.board_rv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        GetBoardList();
    }

    private void GetBoardList() {
        BoardAPI boardAPI = ApiClient.getNoHeaderApiClient().create(BoardAPI.class);
        boardAPI.getBoardList().enqueue(new Callback<List<BoardListModel>>() {
            @Override
            public void onResponse(Call<List<BoardListModel>> call, Response<List<BoardListModel>> response) {
                List<BoardListModel> boardList = response.body();

                Log.d(TAG, "GetBoardList onResponse: " + boardList);

                arrayList = new ArrayList<>();

                mBoardListAdapter = new BoardListAdapter(BoardListActivity.this,arrayList);

                for(int i = 0; i < boardList.size(); ++i)
                {
                    Log.d(TAG, "GetBoardList onResponse: " + boardList.get(i).getBoard_id());
                    arrayList.add(boardList.get(i));
                }
                mRecyclerView.setAdapter(mBoardListAdapter);
            }
            @Override
            public void onFailure(Call<List<BoardListModel>> call, Throwable t) {
                Log.e(TAG, "Set Board onFailure: " + t.getMessage());
            }
        });
    }

    public void postBoard(View view) {
        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);
    }
}
