package kr.ac.uc.matzip.view;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomBoardFragment extends BottomSheetDialogFragment implements PullRefreshLayout.OnRefreshListener {
    private ArrayList<BoardListModel> arrayList;
    private BoardListAdapter mBoardListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PullRefreshLayout loading;
    private BottomSheetBehavior mBehavior;

    private final int board_id;

    private final Context context;

    public BottomBoardFragment(Context context, int board_id) {
        this.context = context;
        this.board_id = board_id;
    }

    @Override   //여기서 dialog를 생성합니다.
    public void setupDialog(@NonNull Dialog dialog, int style) {    //onCreateView 이전에 불립니다.
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_board, null);
        //여기의 메서드를 사용해서 크기를 조절
        GetBoard(board_id);

        dialog.setContentView(contentView);

        mBehavior = BottomSheetBehavior.from((View) contentView.getParent());

        mBehavior.setSkipCollapsed(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBehavior != null) {
            mBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_board, container, false);

        loading = (PullRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.bottom_board_rv);
        mLinearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        loading.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        loading.setOnRefreshListener(this);

        GetBoard(board_id);

        return view;
    }

    private void GetBoard(int board_id) {
        BoardAPI boardAPI = ApiClient.getNoHeaderApiClient().create(BoardAPI.class);
        boardAPI.getBoard(board_id).enqueue(new Callback<List<BoardListModel>>() {
            @Override
            public void onResponse(Call<List<BoardListModel>> call, Response<List<BoardListModel>> response) {
                List<BoardListModel> boardList = response.body();

                Log.d(ContentValues.TAG, "GetBoardList onResponse: " + boardList);

                arrayList = new ArrayList<>();

                mBoardListAdapter = new BoardListAdapter(getContext(),arrayList);

                Log.d(ContentValues.TAG, "GetBoardList onResponse: " + boardList.get(0).getBoard_id());
                arrayList.add(boardList.get(0));

                mRecyclerView.setAdapter(mBoardListAdapter);
            }

            @Override
            public void onFailure(Call<List<BoardListModel>> call, Throwable t) {
                Log.e(ContentValues.TAG, "Set Board onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        GetBoard(board_id);
        loading.setRefreshing(false);
    }
}
