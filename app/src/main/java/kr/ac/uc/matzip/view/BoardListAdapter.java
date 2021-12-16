package kr.ac.uc.matzip.view;

import static kr.ac.uc.matzip.view.FileUtils.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.model.CommentListModel;
import kr.ac.uc.matzip.model.LoveModel;
import kr.ac.uc.matzip.model.PhotoModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import kr.ac.uc.matzip.presenter.CommentAPI;
import kr.ac.uc.matzip.presenter.LoveAPI;
import kr.ac.uc.matzip.presenter.PhotoAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.Board_List_CustomViewHolder> {

    public static final String LIST_LATITUDE = "LIST_LATITUDE";
    public static final String LIST_LONGITUDE = "LIST_LONGITUDE";
    private final Context context;
    private final ArrayList<BoardListModel> Board_Arraylist;
    private ArrayList<CommentListModel> Comment_ArrayList;
    private ArrayList<Uri> imageList;
    private LinearLayoutManager mLinearLayoutManager;
    private MapFragment mapfragment;

    Bundle bundle = new Bundle(2); // 번들을 통해 값 전달

    public BoardListAdapter(Context context,ArrayList<BoardListModel> arraylist) {
        this.context = context;
        this.Board_Arraylist = arraylist;
    }

    @NonNull
    @Override
    public BoardListAdapter.Board_List_CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_list,parent,false);
        Board_List_CustomViewHolder holder = new Board_List_CustomViewHolder(view);

        return holder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull BoardListAdapter.Board_List_CustomViewHolder holder, int position) {
        final int mPosition = position;
        if(Board_Arraylist.get(position).getUser_photo_uri() != null)
        {
            Glide.with(context).load(Board_Arraylist.get(position).getUser_photo_uri()).into(holder.iig_profileIv);
        }
        holder.iig_idTv.setText(Board_Arraylist.get(position).getNickname());
        holder.iig_idTv2.setText(Board_Arraylist.get(position).getNickname());
        holder.iig_titleTv.setText(Board_Arraylist.get(position).getBo_title());
        holder.iig_contIv.setText(Board_Arraylist.get(position).getBo_cont());

        select_photo(holder, Board_Arraylist.get(position).getBoard_id());
        getCommentList(holder, Board_Arraylist.get(position).getBoard_id());
        loved_user(holder, Board_Arraylist.get(position).getBoard_id());
        loved_count(holder, Board_Arraylist.get(position).getBoard_id());

        if(Board_Arraylist.get(position).getLatitude() == null && Board_Arraylist.get(position).getLongitude() == null)
        {
            holder.iig_mapBtn.setVisibility(View.GONE);
        }
        else
        {
            holder.iig_mapBtn.setVisibility(View.VISIBLE);

            holder.iig_mapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewPager2 pager2;
                    pager2 = ViewPagerActivity.pager2;
                    mapfragment = ViewPagerLayoutAdapter.mMapFragment;
                    Log.d(TAG, "onClick: " + Board_Arraylist.get(mPosition).getLatitude());
                    bundle.putDouble("LIST_LATITUDE", Board_Arraylist.get(mPosition).getLatitude());//번들에 넘길 값 저장
                    bundle.putDouble("LIST_LONGITUDE", Board_Arraylist.get(mPosition).getLongitude());
                    Log.d(TAG, "onClick: " + mapfragment);
                    mapfragment.setArguments(bundle);
                    Toast.makeText(context, "해당 위치로 이동중입니다.", Toast.LENGTH_LONG).show();

                    pager2.setCurrentItem(0);
                }
            });
        }

        Intent comment_intent = new Intent(context, CommentActivity.class);

        holder.iig_commentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment_intent.putExtra("board_id", Board_Arraylist.get(mPosition).getBoard_id());
                context.startActivity(comment_intent);
            }
        });

        holder.iig_commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment_intent.putExtra("board_id", Board_Arraylist.get(mPosition).getBoard_id());
                context.startActivity(comment_intent);
            }
        });

        holder.iig_heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.iig_heartBtn.setVisibility(View.INVISIBLE);
                holder.iig_heartLav.setVisibility(View.VISIBLE);
                holder.iig_heartTouch.setVisibility(View.VISIBLE);
                holder.iig_heartLav.playAnimation();
                loved_board(holder, Board_Arraylist.get(mPosition).getBoard_id());
            }
        });


        holder.iig_heartTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.iig_heartLav.setVisibility(View.INVISIBLE);
                holder.iig_heartTouch.setVisibility(View.INVISIBLE);
                holder.iig_heartBtn.setVisibility(View.VISIBLE);
                loved_board(holder, Board_Arraylist.get(mPosition).getBoard_id());
            }
        });

        holder.iig_settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(context, view);

                if(Board_Arraylist.get(mPosition).getUser_id() == SaveSharedPreference.getInt("user_id")) {
                    popupMenu.getMenuInflater().inflate(R.menu.board_login_option_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.board_delete) {
                                DeletePost(Board_Arraylist.get(mPosition).getBoard_id(), mPosition);
                            }
//                            else if (menuItem.getItemId() == R.id.board_update) {
//                            }
                            else if (menuItem.getItemId() == R.id.board_report) {
                                Toast.makeText(context, "신고가 접수 되었습니다.", Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
                }
                else
                {
                    popupMenu.getMenuInflater().inflate(R.menu.board_option_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.board_report) {
                                Toast.makeText(context, "신고가 접수 되었습니다.", Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
                }
                popupMenu.show();
            }
        });

        holder.iig_profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BoardUserActivity.class);
                intent.putExtra("user_id", Board_Arraylist.get(mPosition).getUser_id());
                Log.d(TAG, "onClick: " + Board_Arraylist.get(mPosition).getUser_id());
                context.startActivity(intent);
            }
        });


        loved_check(holder, Board_Arraylist.get(position).getBoard_id());
    }

    @Override
    public int getItemCount() {
        return (null != Board_Arraylist ? Board_Arraylist.size() : 0);
    }

    public static class Board_List_CustomViewHolder extends RecyclerView.ViewHolder {

        protected ViewPager viewPager;
        protected ImageView iig_profileIv;
        protected RecyclerView iig_commentRv;
        protected ViewPager iig_photoVP;
        protected TextView iig_idTv,iig_titleTv,iig_idTv2,iig_contIv,iig_commentTv,iig_likeTv1,iig_likeTv2,iig_likeTv3,iig_likeTv4;
        protected Button iig_heartBtn, iig_commentBtn, iig_mapBtn, iig_settingBtn;
        protected LottieAnimationView iig_heartLav;
        protected View iig_heartTouch;

        public Board_List_CustomViewHolder(View itemView){
            super(itemView);
            this.viewPager = (ViewPager) itemView.findViewById(R.id.vp_pagerVp);
            this.iig_profileIv = (ImageView) itemView.findViewById(R.id.iig_profileIv);
            this.iig_photoVP = (ViewPager) itemView.findViewById(R.id.iig_photoVP);

            this.iig_idTv = (TextView) itemView.findViewById(R.id.iig_idTv);
            this.iig_titleTv = (TextView) itemView.findViewById(R.id.iig_titleTv);
            this.iig_idTv2 = (TextView) itemView.findViewById(R.id.iig_idTv2);
            this.iig_contIv = (TextView) itemView.findViewById(R.id.iig_contIv);
            this.iig_commentTv = (TextView) itemView.findViewById(R.id.iig_commentTv);
            this.iig_likeTv1 = (TextView) itemView.findViewById(R.id.iig_likeTv1);
            this.iig_likeTv2 = (TextView) itemView.findViewById(R.id.iig_likeTv2);
            this.iig_likeTv3 = (TextView) itemView.findViewById(R.id.iig_likeTv3);
            this.iig_likeTv4 = (TextView) itemView.findViewById(R.id.iig_likeTv4);
            
            this.iig_commentBtn = (Button) itemView.findViewById(R.id.iig_commentBtn);
            iig_heartBtn = (Button) itemView.findViewById(R.id.iig_heartBtn);
            this.iig_mapBtn = (Button) itemView.findViewById(R.id.iig_mapBtn);
            this.iig_settingBtn = (Button) itemView.findViewById(R.id.iig_settingBtn);

            this.iig_commentRv = (RecyclerView) itemView.findViewById(R.id.iig_commentRv);
            this.iig_heartLav = itemView.findViewById(R.id.iig_heartLav);
            this.iig_heartTouch = itemView.findViewById(R.id.iig_heartTouch);
        }
    }

    private void select_photo(@NonNull BoardListAdapter.Board_List_CustomViewHolder holder, Integer bo_id) {
        PhotoAPI photoAPI = ApiClient.getNoHeaderApiClient().create(PhotoAPI.class);
        photoAPI.select_photo(bo_id).enqueue(new Callback<List<PhotoModel>>() {
            @Override
            public void onResponse(Call<List<PhotoModel>> call, Response<List<PhotoModel>> response) {
                List<PhotoModel> res = response.body();

                imageList = new ArrayList<>();

                if(res.size() != 0) {

                    for (int i = 0; i < res.size(); ++i) {
                        imageList.add(Uri.parse(res.get(i).getPhoto_uri()));
                    }

                    holder.iig_photoVP.setAdapter(new ImageViewPagerAdapter(context, imageList));

                    holder.iig_photoVP.setClipToPadding(false);

                    holder.iig_photoVP.setPadding(100, 0, 100, 0);
                    holder.iig_photoVP.setPageMargin(50);

                    Log.d(TAG, "select_photo onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<List<PhotoModel>> call, Throwable t) {
                Log.e(TAG, "select_photo onFailure: " + t.getMessage());
            }
        });
    }

    private void getCommentList(@NonNull BoardListAdapter.Board_List_CustomViewHolder holder, int board_id) {
        CommentAPI commentAPI = ApiClient.getNoHeaderApiClient().create(CommentAPI.class);
        commentAPI.getCommentList(board_id).enqueue(new Callback<List<CommentListModel>>() {
            @Override
            public void onResponse(Call<List<CommentListModel>> call, Response<List<CommentListModel>> response) {
                List<CommentListModel> commentList = response.body();

                Log.d(ContentValues.TAG, "getCommentList: " + commentList.size());

                if(commentList.size() != 0) {
                    Comment_ArrayList = new ArrayList<>();

                    mLinearLayoutManager = new LinearLayoutManager(context);

                    holder.iig_commentRv.setLayoutManager(mLinearLayoutManager);

                    holder.iig_commentRv.setAdapter(new CommentAdapter(context, Comment_ArrayList));

                    for (int i = 0; i < 1; ++i) {
                        Comment_ArrayList.add(commentList.get(i));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<CommentListModel>> call, Throwable t) {
                Log.e(ContentValues.TAG, "Set Board onFailure: " + t.getMessage());
            }
        });
    }
    //TODO PHP 오류 잡아야됨
    private void loved_board(@NonNull BoardListAdapter.Board_List_CustomViewHolder holder, Integer bo_id) {
//        if(holder.iig_heartBtn.getBackground() != context.getDrawable(full_heart)){
//            holder.iig_heartBtn.setBackground(context.getDrawable(full_heart));
//        }
//        else {
//            holder.iig_heartBtn.setBackground(context.getDrawable(heart));
//        }

        LoveAPI loveAPI = ApiClient.getApiClient().create(LoveAPI.class);
        loveAPI.love_post(bo_id).enqueue(new Callback<LoveModel>() {
            @Override
            public void onResponse(Call<LoveModel> call, Response<LoveModel> response) {
                LoveModel res = response.body();
                Log.d(TAG, "loved_board onResponse: " + res);

//                holder.iig_heartBtn.setBackground(context.getDrawable(heart));
//                loved_user(holder, bo_id);
//                loved_count(holder, bo_id);
                loved_check(holder, bo_id);
            }
            @Override
            public void onFailure(Call<LoveModel> call, Throwable t) {
                Log.e(TAG, "loved_board onFailure: " + t.getMessage());
//                Intent intent = new Intent(context, LoginActivity.class);
//                context.startActivity(intent);
//                holder.iig_heartBtn.setBackground(context.getDrawable(full_heart));
                loved_check(holder, bo_id);
            }
        });

    }

    //TODO 버그 있음
    private void loved_check(@NonNull BoardListAdapter.Board_List_CustomViewHolder holder, Integer bo_id) {
        LoveAPI loveAPI = ApiClient.getApiClient().create(LoveAPI.class);
        loveAPI.love_check(bo_id).enqueue(new Callback<LoveModel>() {

            @Override
            public void onResponse(@NonNull Call<LoveModel> call, @NonNull Response<LoveModel> response) {
                Integer res = response.body().getBoard_id();
                if(response.body().getBoard_id() != 0) {
                    holder.iig_heartBtn.setVisibility(View.INVISIBLE);
                    holder.iig_heartLav.setVisibility(View.VISIBLE);
                    holder.iig_heartTouch.setVisibility(View.VISIBLE);
                }else{
                    holder.iig_heartBtn.setVisibility(View.VISIBLE);
                    holder.iig_heartLav.setVisibility(View.INVISIBLE);
                    holder.iig_heartTouch.setVisibility(View.INVISIBLE);
                }

//                if(response.body().getBoard_id() == 0){
//                    holder.iig_heartBtn.setBackground(context.getDrawable(heart));
//                }
                Log.d(TAG, "loved_check onResponse: " + res);
            }

            @Override
            public void onFailure(Call<LoveModel> call, Throwable t) {
                Log.e(TAG, "loved_check onFailure: " + t.getMessage());
            }
        });
        loved_user(holder, bo_id);
    }

    private void loved_user(@NonNull BoardListAdapter.Board_List_CustomViewHolder holder, Integer bo_id) {
        LoveAPI loveAPI = ApiClient.getNoHeaderApiClient().create(LoveAPI.class);
        loveAPI.love_user(bo_id).enqueue(new Callback<LoveModel>() {

            @Override
            public void onResponse(Call<LoveModel> call,@NonNull Response<LoveModel> response) {
                LoveModel res = response.body();
                Log.d(TAG, "onResponse: ");
                if(res != null) {
                    holder.iig_likeTv1.setVisibility(View.VISIBLE);
                    holder.iig_likeTv2.setVisibility(View.VISIBLE);
                    holder.iig_likeTv3.setVisibility(View.VISIBLE);
                    holder.iig_likeTv4.setVisibility(View.VISIBLE);
                    holder.iig_likeTv1.setText(res.getNickname());
                    loved_count(holder, bo_id);
                } else {
                    holder.iig_likeTv1.setVisibility(View.INVISIBLE);
                    holder.iig_likeTv2.setVisibility(View.INVISIBLE);
                    holder.iig_likeTv3.setVisibility(View.INVISIBLE);
                    holder.iig_likeTv4.setVisibility(View.INVISIBLE);
                }

//                if(response.body().getBoard_id() == 0){
//                    holder.iig_heartBtn.setBackground(context.getDrawable(heart));
//                }
                Log.d(TAG, "loved_user onResponse: " + res);
            }

            @Override
            public void onFailure(Call<LoveModel> call, Throwable t) {
                Log.e(TAG, "loved_user onFailure: " + t.getMessage());
            }
        });
    }

    private void loved_count(@NonNull BoardListAdapter.Board_List_CustomViewHolder holder, Integer bo_id) {
        LoveAPI loveAPI = ApiClient.getNoHeaderApiClient().create(LoveAPI.class);
        loveAPI.love_count(bo_id).enqueue(new Callback<LoveModel>() {

            @Override
            public void onResponse(Call<LoveModel> call, Response<LoveModel> response) {
                LoveModel res = response.body();
                if(res.getCount() != 0) {
                    holder.iig_likeTv3.setText(String.valueOf(res.getCount()));
                } else {
                    holder.iig_likeTv3.setText("0");
                }

//                if(response.body().getBoard_id() == 0){
//                    holder.iig_heartBtn.setBackground(context.getDrawable(heart));
//                }
                Log.d(TAG, "loved_count onResponse: " + res.getCount());
            }

            @Override
            public void onFailure(Call<LoveModel> call, Throwable t) {
                Log.e(TAG, "loved_count onFailure: " + t.getMessage());
            }
        });
    }

    private void DeletePost(int board_id, int position){
        BoardAPI boardAPI = ApiClient.getNoHeaderApiClient().create(BoardAPI.class);
        boardAPI.deletePost(board_id).enqueue(new Callback<BoardModel>() {
            @Override
            public void onResponse(@NonNull Call<BoardModel> call, @NonNull Response<BoardModel> response) {
                BoardModel res = response.body();

                Log.d(TAG, "DeletePost onResponse: " + res.getSuccess());
                bindDelete(position);
                Toast.makeText(context, "게시글을 삭제하였습니다.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<BoardModel> call, @NonNull Throwable t) {
                Log.e(TAG, "DeletePost onFailure: " + t.getMessage());
                Toast.makeText(context, "게시글 삭제에 실패하였습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void bindDelete(int position) {
        // 선택한 item 삭제
        Board_Arraylist.remove(position);
        // 선택 항목 삭제
        notifyItemRemoved(position);
        // List 반영
        notifyDataSetChanged();
    }
}
