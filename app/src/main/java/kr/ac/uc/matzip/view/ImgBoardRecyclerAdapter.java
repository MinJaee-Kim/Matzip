package kr.ac.uc.matzip.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.presenter.ImgBoardPresenter;

public class ImgBoardRecyclerAdapter extends RecyclerView.Adapter<ImgBoardRecyclerAdapter.ViewHolder> {
    private final List<ImgBoardPresenter> boardList;
    private static final String TAG = "Adapter";
    public ImgBoardRecyclerAdapter(List<ImgBoardPresenter> boardList) {
        this.boardList = boardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //뷰홀더 레이아웃
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {    //뷰홀더에 저장할 사진
        holder.imgBoardPresenter = boardList.get(position);

//        holder.boardName.setText(boardList.get(position).getName());
        Picasso.get()
                .load("https://square.github.io/picasso/static/sample.png")
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() { //아이템 갯수
        Log.d(TAG, "getItemCount: " + boardList.size());
        return boardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{    //뷰홀더 요소 선언
        final View parentView;
        final ImageView imageView;
//        final TextView boardName;
        ImgBoardPresenter imgBoardPresenter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentView = itemView;
            imageView = itemView.findViewById(R.id.imageView);
//            boardName = itemView.findViewById(R.id.item_name);
            parentView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ");
//                    if(mListener != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            mListener.onItemClick(parentView, position);
//                        }
//                    }
                }
            });
        }
    }

}
