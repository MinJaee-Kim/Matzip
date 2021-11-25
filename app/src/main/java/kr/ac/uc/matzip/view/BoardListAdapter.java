package kr.ac.uc.matzip.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardModel;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.CustomViewHolder> {

    private ArrayList<BoardModel> arraylist;

    public BoardListAdapter(ArrayList<BoardModel> arraylist) {
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public BoardListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoardListAdapter.CustomViewHolder holder, int position) {
        holder.iig_profileIv.setImageResource(arraylist.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iig_profileIv,iig_photoIv;
        protected TextView iig_idTv,iig_titleTv,iig_idTv2,iig_contIv,iig_commentTv,iig_likeTv;
        protected Button iig_heartBtn,iig_commentBtn;

        public CustomViewHolder(View itemView){
            super(itemView);
            this.iig_profileIv = (ImageView) itemView.findViewById(R.id.iig_profileIv);
            this.iig_photoIv = (ImageView) itemView.findViewById(R.id.iig_photoIv);
            this.iig_idTv = (TextView) itemView.findViewById(R.id.iig_idTv);
            this.iig_titleTv = (TextView) itemView.findViewById(R.id.iig_titleTv);
            this.iig_idTv2 = (TextView) itemView.findViewById(R.id.iig_idTv2);
            this.iig_contIv = (TextView) itemView.findViewById(R.id.iig_contIv);
            this.iig_commentTv = (TextView) itemView.findViewById(R.id.iig_commentTv);
            this.iig_likeTv = (TextView) itemView.findViewById(R.id.iig_likeTv);
            this.iig_heartBtn = (Button) itemView.findViewById(R.id.iig_heartBtn);
            this.iig_commentBtn = (Button) itemView.findViewById(R.id.iig_commentBtn);
        }
    }
}
