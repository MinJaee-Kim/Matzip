package kr.ac.uc.matzip.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.CommentListModel;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<CommentListModel> arraylist;
    private ArrayList<Uri> imageList;

    public CommentAdapter(Context context,ArrayList<CommentListModel> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
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
        if(arraylist.get(position).getUser_photo_uri() != null)
        {
            Glide.with(context).load(arraylist.get(position).getUser_photo_uri()).into(holder.comm_profileIv);
        }
        holder.comm_profileTv.setText(arraylist.get(position).getNickname());
        holder.comm_commentTv.setText(arraylist.get(position).getCo_cont());
    }

    @Override
    public int getItemCount() {
        return (null != arraylist ? arraylist.size() : 0);
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
