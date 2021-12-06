package kr.ac.uc.matzip.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.model.KakaoModel;

public class MapSearchAdapter extends RecyclerView.Adapter<MapSearchAdapter.CustomViewHolder>{

    private Context context;
    private ArrayList<KakaoModel> Kakao_Arraylist;

    public MapSearchAdapter(Context context, ArrayList<BoardListModel> arraylist) {
        this.context = context;
        this.Kakao_Arraylist = arraylist;
    }

    @NonNull
    @Override
    public CommentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment,parent,false);
        CommentAdapter.CustomViewHolder holder = new CommentAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CustomViewHolder holder, int position) {
        holder.comm_profileTv.setText(Kakao_Arraylist.get(position).getNickname());
        holder.comm_commentTv.setText(Kakao_Arraylist.get(position).getCo_cont());
    }

    @Override
    public int getItemCount() {
        return (null != Kakao_Arraylist ? Kakao_Arraylist.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView comm_profileIv;
        protected TextView comm_profileTv,comm_commentTv;

        public CustomViewHolder(View itemView){
            super(itemView);
            this.comm_profileIv = (ImageView) itemView.findViewById(R.id.comm_profileIv);
            this.comm_profileTv = (TextView) itemView.findViewById(R.id.comm_profileTv);
            this.comm_commentTv = (TextView) itemView.findViewById(R.id.comm_commentTv);
        }
    }
}
