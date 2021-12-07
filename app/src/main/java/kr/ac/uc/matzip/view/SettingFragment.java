package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardListModel;
import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import kr.ac.uc.matzip.presenter.MemberAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragment extends androidx.fragment.app.Fragment implements PullRefreshLayout.OnRefreshListener {

    private ArrayList<BoardListModel> arrayList;
    private BoardListAdapter mBoardListAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PullRefreshLayout loading;

    private ImageView profile_photoIv;
    private TextView profile_nickTv;
    private Button loginBtn;

    private View view;

    public static SettingFragment newInstance() {
        SettingFragment settingFragment = new SettingFragment();
        return settingFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_design, container, false);

        loading = (PullRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.profile_manageRv);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        profile_nickTv = view.findViewById(R.id.profile_nickTv);
        profile_photoIv = view.findViewById(R.id.profile_photoIv);

        loading.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        loading.setOnRefreshListener(this);

        settingProfile();
        GetBoardList();

        Button board_postBtn = (Button) view.findViewById(R.id.profile_editBtn);
        loginBtn = view.findViewById(R.id.profile_loginBtn);
        board_postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileSettingActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginBtn.setVisibility(View.GONE);
    }

    private void settingProfile() {
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.getProfile().enqueue(new Callback<List<MemberModel>>() {
            @Override
            public void onResponse(Call<List<MemberModel>> call, Response<List<MemberModel>> response) {
                assert response.body() != null;
                MemberModel res = response.body().get(0);

                Log.d(TAG, "settingProfile onResponse: " + res);

                profile_nickTv.setText(res.getNickname());

                if(res.getUser_photo_uri() != null)
                {
                    Glide.with(getContext()).load(res.getUser_photo_uri()).into(profile_photoIv);
                }
            }

            @Override
            public void onFailure(Call<List<MemberModel>> call, Throwable t) {
                Log.e(TAG, "settingProfile onFailure: " + t.getMessage());
                loginBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void GetBoardList() {
        if(!SaveSharedPreference.getString("token").equals("")) {
            BoardAPI boardAPI = ApiClient.getApiClient().create(BoardAPI.class);
            boardAPI.getUserBoardList().enqueue(new Callback<List<BoardListModel>>() {
                @Override
                public void onResponse(Call<List<BoardListModel>> call, Response<List<BoardListModel>> response) {
                    List<BoardListModel> boardList = response.body();

                    Log.d(TAG, "GetBoardList onResponse: " + boardList);

                    arrayList = new ArrayList<>();

                    mBoardListAdapter = new BoardListAdapter(getContext(), arrayList);

                    for (int i = 0; i < boardList.size(); ++i) {
                        Log.d(TAG, "GetBoardList onResponse: " + boardList.get(i).getBoard_id());
                        arrayList.add(boardList.get(i));
                    }
                    mRecyclerView.setAdapter(mBoardListAdapter);
                }

                @Override
                public void onFailure(Call<List<BoardListModel>> call, Throwable t) {
                    Log.e(TAG, "Set Board onFailure: " + t.getMessage());

                }
            });
        }
    }

    @Override
    public void onRefresh() {
        GetBoardList();
        loading.setRefreshing(false);
    }
}