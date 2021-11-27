package kr.ac.uc.matzip.view;

import static kr.ac.uc.matzip.view.FileUtils.TAG;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.model.PhotoModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.PhotoAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.CustomViewHolder> {

    private Context context;
    private ArrayList<BoardListModel> arraylist;
    private ArrayList<Uri> imageList;

    public BoardListAdapter(Context context,ArrayList<BoardListModel> arraylist) {
        this.context = context;
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
        holder.iig_idTv.setText(arraylist.get(position).getNickname());
        holder.iig_idTv2.setText(arraylist.get(position).getNickname());
        holder.iig_titleTv.setText(arraylist.get(position).getBo_title());
        holder.iig_contIv.setText(arraylist.get(position).getBo_cont());

        select_photo(holder, arraylist.get(position).getBoard_id());
    }

    @Override
    public int getItemCount() {
        return (null != arraylist ? arraylist.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iig_profileIv;
        protected ViewPager iig_photoVP;
        protected TextView iig_idTv,iig_titleTv,iig_idTv2,iig_contIv,iig_commentTv,iig_likeTv;
        protected Button iig_heartBtn,iig_commentBtn;

        public CustomViewHolder(View itemView){
            super(itemView);
            this.iig_profileIv = (ImageView) itemView.findViewById(R.id.iig_profileIv);
            this.iig_photoVP = (ViewPager) itemView.findViewById(R.id.iig_photoVP);
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

    private void select_photo(@NonNull BoardListAdapter.CustomViewHolder holder, Integer bo_id) {
        PhotoAPI photoAPI = ApiClient.getApiClient().create(PhotoAPI.class);
        photoAPI.select_photo(bo_id).enqueue(new Callback<List<PhotoModel>>() {
            @Override
            public void onResponse(Call<List<PhotoModel>> call, Response<List<PhotoModel>> response) {
                List<PhotoModel> res = response.body();

                imageList = new ArrayList<>();

                if(res.size() != 0) {

                    for (int i = 0; i < res.size(); ++i) {
                        Log.d(TAG, "onResponse: ");
                        imageList.add(Uri.parse(res.get(i).getPhoto_uri()));
                    }

                    holder.iig_photoVP.setAdapter(new ViewPagerAdapter(context, imageList));

                    holder.iig_photoVP.setClipToPadding(false);

                    holder.iig_photoVP.setPadding(100, 0, 100, 0);
                    holder.iig_photoVP.setPageMargin(50);

                    Log.d(TAG, "select_photo onResponse: " + res.get(0).getPhoto_uri());
                }
            }

            @Override
            public void onFailure(Call<List<PhotoModel>> call, Throwable t) {
                Log.d(TAG, "select_photo onFailure: " + t.getMessage());
            }
        });
    }
}