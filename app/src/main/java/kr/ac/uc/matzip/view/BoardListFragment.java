package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardListFragment extends androidx.fragment.app.Fragment implements PullRefreshLayout.OnRefreshListener {

    private ArrayList<BoardListModel> arrayList;
    private BoardListAdapter mBoardListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PullRefreshLayout loading;

    private View view;

    public static BoardListFragment newInstance() {
        return new BoardListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.board_layout, container, false);

        loading = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.board_rv);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        loading.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        loading.setOnRefreshListener(this);

        Button board_postBtn = (Button) view.findViewById(R.id.board_postBtn);
        board_postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        GetBoardList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void GetBoardList() {
        BoardAPI boardAPI = ApiClient.getNoHeaderApiClient().create(BoardAPI.class);
        boardAPI.getBoardList().enqueue(new Callback<List<BoardListModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<BoardListModel>> call, @NonNull Response<List<BoardListModel>> response) {
                List<BoardListModel> boardList = response.body();

                Log.d(TAG, "GetBoardList onResponse: " + boardList);

                arrayList = new ArrayList<>();

                mBoardListAdapter = new BoardListAdapter(getContext(), arrayList);
                assert boardList != null;

                arrayList.addAll(boardList);

                mRecyclerView.setAdapter(mBoardListAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<BoardListModel>> call, @NonNull Throwable t) {
                Log.e(TAG, "Set Board onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        GetBoardList();
        loading.setRefreshing(false);
    }
}
