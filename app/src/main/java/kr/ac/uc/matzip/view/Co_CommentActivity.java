package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.Co_CommentListModel;
import kr.ac.uc.matzip.model.CommentListModel;
import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.Co_CommentAPI;
import kr.ac.uc.matzip.presenter.CommentAPI;
import kr.ac.uc.matzip.presenter.MemberAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Co_CommentActivity extends AppCompatActivity implements PullRefreshLayout.OnRefreshListener {

    private ArrayList<Co_CommentListModel> arrayList;
    private Co_CommentAdapter mCommentAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PullRefreshLayout loading;

    private EditText co_comment_coEt;
    private TextView co_comment_btnTv,comm_profileTv,comm_commentTv,comm_moveCo_comm;
    private ImageView co_comment_profileIv, comm_profileIv;

    private int comment_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.co_comment_layout);

        comm_profileTv = findViewById(R.id.comm_profileTv);
        comm_commentTv = findViewById(R.id.comm_commentTv);
        comm_profileIv = findViewById(R.id.comm_profileIv);
        comm_moveCo_comm = findViewById(R.id.comm_moveCo_comm);
        comm_moveCo_comm.setVisibility(View.GONE);

        co_comment_coEt = findViewById(R.id.co_comment_coEt);
        co_comment_btnTv = findViewById(R.id.co_comment_btnTv);
        co_comment_profileIv = findViewById(R.id.co_comment_profileIv);

        mRecyclerView = (RecyclerView)findViewById(R.id.co_comment_rv);

        mLinearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        loading = (PullRefreshLayout)findViewById(R.id.swipeRefreshLayout);

        loading.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        loading.setOnRefreshListener(this);

        settingProfile();

        Intent comment_intent = getIntent();
        comment_id = comment_intent.getIntExtra("comment_id", 0);

        getCommentIdPost(comment_id);
        getCo_CommentList(comment_id);

        co_comment_btnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!co_comment_coEt.getText().toString().equals(""))
                {
                    postCo_Comment(comment_id, co_comment_coEt.getText().toString());
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingProfile();
        getCo_CommentList(comment_id);
    }

    private void settingProfile() {
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.getProfile().enqueue(new Callback<List<MemberModel>>() {
            @Override
            public void onResponse(Call<List<MemberModel>> call, Response<List<MemberModel>> response) {
                assert response.body() != null;
                MemberModel res = response.body().get(0);

                Log.d(TAG, "settingProfile onResponse: " + res);

                if(res.getUser_photo_uri() != null)
                {
                    Glide.with(Co_CommentActivity.this).load(res.getUser_photo_uri()).into(co_comment_profileIv);
                }
            }

            @Override
            public void onFailure(Call<List<MemberModel>> call, Throwable t) {
                Log.e(TAG, "settingProfile onFailure: " + t.getMessage());
            }
        });
    }

    private void getCommentIdPost(int comment_id) {
        CommentAPI commentAPI = ApiClient.getNoHeaderApiClient().create(CommentAPI.class);
        commentAPI.getCommentId(comment_id).enqueue(new Callback<List<CommentListModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CommentListModel>> call, @NonNull Response<List<CommentListModel>> response) {
                assert response.body() != null;
                CommentListModel comment = response.body().get(0);

                Log.d(TAG, "getCommentList: " + comment);

                if(comment.getUser_photo_uri() != null)
                {
                    Glide.with(Co_CommentActivity.this).load(comment.getUser_photo_uri()).into(comm_profileIv);
                }
                comm_profileTv.setText(comment.getNickname());
                comm_commentTv.setText(comment.getCo_cont());
            }
            @Override
            public void onFailure(@NonNull Call<List<CommentListModel>> call, @NonNull Throwable t) {
                Log.e(TAG, "Set Board onFailure: " + t.getMessage());
            }
        });
    }

    private void getCo_CommentList(int comment_id) {
        Co_CommentAPI co_commentAPI = ApiClient.getNoHeaderApiClient().create(Co_CommentAPI.class);
        co_commentAPI.getCo_CommentList(comment_id).enqueue(new Callback<List<Co_CommentListModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<Co_CommentListModel>> call, @NonNull Response<List<Co_CommentListModel>> response) {
                List<Co_CommentListModel> commentList = response.body();

                Log.d(TAG, "getCommentList: " + commentList.size());

                if(commentList.size() != 0) {
                    arrayList = new ArrayList<>();

                    mCommentAdapter = new Co_CommentAdapter(Co_CommentActivity.this, arrayList);

                    arrayList.addAll(commentList);

                    mRecyclerView.setAdapter(mCommentAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Co_CommentListModel>> call, @NonNull Throwable t) {
                Log.e(TAG, "Set Board onFailure: " + t.getMessage());
            }
        });
    }

    private void postCo_Comment(int comment_id, String cont)
    {
        Co_CommentAPI co_commentAPI = ApiClient.getApiClient().create(Co_CommentAPI.class);
        co_commentAPI.postCo_Comment(comment_id, cont).enqueue(new Callback<Co_CommentListModel>()
        {
            @Override
            public void onResponse(@NonNull Call<Co_CommentListModel> call, @NonNull Response<Co_CommentListModel> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"댓글 작성에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    co_comment_coEt.setText("");
                    getCo_CommentList(comment_id);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"로그인 해주세요.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Co_CommentActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Co_CommentListModel> call, @NonNull Throwable t) {
                Log.e(TAG, "postComment onFailure: " + t.getMessage());
                Intent intent = new Intent(Co_CommentActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        getCo_CommentList(comment_id);
        loading.setRefreshing(false);
    }

    public void goBack(View view) {
        this.finish();
    }

}