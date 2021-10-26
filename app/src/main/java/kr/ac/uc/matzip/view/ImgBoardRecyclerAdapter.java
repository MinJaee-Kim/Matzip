package kr.ac.uc.matzip.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.presenter.ImgBoardPresenter;

public class ImgBoardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ImgBoardPresenter> boardList;
    public ImgBoardRecyclerAdapter(List<ImgBoardPresenter> boardList) {
        this.boardList = boardList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_row, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
