package kr.ac.uc.matzip.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.presenter.ImgBoardPresenter;

public class ImgBoardListFragment extends Fragment {
    private ArrayList<ImgBoardPresenter> mBoardArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ImgBoardRecyclerAdapter mImgBoardRecyclerAdapter = new ImgBoardRecyclerAdapter(mBoardArrayList);
    private static final String TAG = "확인";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setupViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imgboard_fragment_list, container, false);
        mRecyclerView = view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context = view.getContext();
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mImgBoardRecyclerAdapter);
    }

    void setBoard(List<ImgBoardPresenter> boardList){
        Log.d(TAG, "setBoard: check");
        for(ImgBoardPresenter imgBoardPresenter : boardList){
            if(!mBoardArrayList.contains(imgBoardPresenter)){
                mBoardArrayList.add(imgBoardPresenter);
                mImgBoardRecyclerAdapter.notifyItemInserted(mBoardArrayList.indexOf(imgBoardPresenter));
            }
        }

//        mImgBoardRecyclerAdapter.setOnRowClickListener(new ImgBoardRecyclerAdapter.OnRowClickListener() {
//            @Override
//            public void onItemClick(View itemView, int position) {
//                ImgBoardPresenter board = boardList.get(position);
//                Intent intent = new Intent(getContext(), BoardDetailActivity.class);
//                intent.putExtra("board", board);
//                startActivity(intent);
//            }
//        });
    }

    //액션바 수정
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.add_menu, menu);
//    }

    //옵션 클릭
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

//    private void setupViewModel(){
//        CatalogViewModel viewModel = ViewModelProviders.of(this)
//                .get(CatalogViewModel.class);
//        viewModel.getBoards().observe(getViewLifecycleOwner(), new Observer<List<Board>>() {
//            @Override
//            public void onChanged(List<ImgBoardPresenter> imgBoardPresenters) {
//                for(ImgBoardPresenter imgBoardPresenters : imgBoardPresenters){
//                    if(!mBoardArrayList.contains(board)){
//                        mBoardArrayList.add(board);
//                        mImgBoardRecyclerAdapter.notifyItemInserted(mBoardArrayList.indexOf(board));
//                    }
//                }
//
//                ImgBoardRecyclerAdapter.setOnRowClickListener(new ImgBoardRecyclerAdapter().OnRowClickListener() {
//                    @Override
//                    public void onItemClick(View itemView, int position) {
//                        Board board = mBoardArrayList.get(position);
//                        Intent intent = new Intent(getContext(), BoardDetailActivity.class);
//                        intent.putExtra("board", board);
//                        startActivity(intent);
//                    }
//                });
//            }
//        });
//    }
}
