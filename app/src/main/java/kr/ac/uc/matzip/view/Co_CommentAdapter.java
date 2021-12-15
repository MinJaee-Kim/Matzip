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
import kr.ac.uc.matzip.model.Co_CommentListModel;
import kr.ac.uc.matzip.model.CommentListModel;

public class Co_CommentAdapter extends RecyclerView.Adapter<Co_CommentAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<Co_CommentListModel> arraylist;
    private ArrayList<Uri> imageList;

    public Co_CommentAdapter(Context context, ArrayList<Co_CommentListModel> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public Co_CommentAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_co_comment,parent,false);
        Co_CommentAdapter.CustomViewHolder holder = new Co_CommentAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Co_CommentAdapter.CustomViewHolder holder, int position) {
        if(arraylist.get(position).getUser_photo_uri() != null)
        {
            Glide.with(context).load(arraylist.get(position).getUser_photo_uri()).into(holder.co_comm_profileIv);
        }
        holder.co_comm_profileTv.setText(arraylist.get(position).getNickname());
        holder.co_comm_commentTv.setText(arraylist.get(position).getCo_co_cont());
    }

    @Override
    public int getItemCount() {
        return (null != arraylist ? arraylist.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView co_comm_profileIv;
        protected TextView co_comm_profileTv,co_comm_commentTv;

        public CustomViewHolder(View itemView){
            super(itemView);
            this.co_comm_profileIv = (ImageView) itemView.findViewById(R.id.co_comm_profileIv);
            this.co_comm_profileTv = (TextView) itemView.findViewById(R.id.co_comm_profileTv);
            this.co_comm_commentTv = (TextView) itemView.findViewById(R.id.co_comm_commentTv);
        }
    }
}
