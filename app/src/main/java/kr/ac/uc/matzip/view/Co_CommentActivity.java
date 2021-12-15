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
import kr.ac.uc.matzip.model.CommentListModel;
import kr.ac.uc.matzip.model.CommentModel;
import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.CommentAPI;
import kr.ac.uc.matzip.presenter.MemberAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Co_CommentActivity extends AppCompatActivity implements PullRefreshLayout.OnRefreshListener {

    private ArrayList<CommentListModel> arrayList;
    private CommentAdapter mCommentAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PullRefreshLayout loading;

    private EditText comment_coEt;
    private TextView comment_btnTv;
    private ImageView comment_profileIv;

    private int board_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_layout);

        comment_coEt = findViewById(R.id.comment_coEt);
        comment_btnTv = findViewById(R.id.comment_btnTv);
        comment_profileIv = findViewById(R.id.comment_profileIv);

        mRecyclerView = (RecyclerView)findViewById(R.id.comment_rv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        loading = (PullRefreshLayout)findViewById(R.id.swipeRefreshLayout);

        loading.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        loading.setOnRefreshListener(this);

        settingProfile();

        Intent comment_intent = getIntent();
        board_id = comment_intent.getIntExtra("board_id", 0);

        getCommentList(board_id);

        comment_btnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!comment_coEt.getText().toString().equals(""))
                {
                    postComment(board_id);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingProfile();
        getCommentList(board_id);
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
                    Glide.with(Co_CommentActivity.this).load(res.getUser_photo_uri()).into(comment_profileIv);
                }
            }

            @Override
            public void onFailure(Call<List<MemberModel>> call, Throwable t) {
                Log.e(TAG, "settingProfile onFailure: " + t.getMessage());
            }
        });
    }

    private void getCommentList(int board_id) {
        CommentAPI commentAPI = ApiClient.getNoHeaderApiClient().create(CommentAPI.class);
        commentAPI.getCommentList(board_id).enqueue(new Callback<List<CommentListModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CommentListModel>> call, @NonNull Response<List<CommentListModel>> response) {
                List<CommentListModel> commentList = response.body();

                Log.d(TAG, "getCommentList: " + commentList.size());

                if(commentList.size() != 0) {
                    arrayList = new ArrayList<>();

                    mCommentAdapter = new CommentAdapter(Co_CommentActivity.this, arrayList);

                    arrayList.addAll(commentList);

                    mRecyclerView.setAdapter(mCommentAdapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<CommentListModel>> call, @NonNull Throwable t) {
                Log.e(TAG, "Set Board onFailure: " + t.getMessage());
            }
        });
    }

    private void postComment(int board_id)
    {
        final String cont = comment_coEt.getText().toString();

        CommentAPI commentAPI = ApiClient.getApiClient().create(CommentAPI.class);
        commentAPI.postComment(board_id, cont).enqueue(new Callback<CommentModel>()
        {
            @Override
            public void onResponse(@NonNull Call<CommentModel> call, @NonNull Response<CommentModel> response) {
                CommentModel res = response.body();

                Log.d(TAG, "postComment: " + res.getBoard_id());

                if(response.isSuccessful() && res.getSuccess() == "true")
                {
                    Toast.makeText(getApplicationContext(),"댓글 작성에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    comment_coEt.setText("");
                    getCommentList(board_id);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"로그인 해주세요.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Co_CommentActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentModel> call, @NonNull Throwable t) {
                Log.e(TAG, "postComment onFailure: " + t.getMessage());
                Intent intent = new Intent(Co_CommentActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        getCommentList(board_id);
        loading.setRefreshing(false);
    }

    public void goBack(View view) {
        this.finish();
    }

}