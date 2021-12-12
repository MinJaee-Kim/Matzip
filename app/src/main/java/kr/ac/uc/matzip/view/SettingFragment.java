package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import static kr.ac.uc.matzip.R.drawable.user;

import android.content.Intent;
import android.opengl.Visibility;
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
    private Button loginBtn, editBtn, settingBtn;

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

        editBtn = view.findViewById(R.id.profile_editBtn);
        loginBtn = view.findViewById(R.id.profile_loginBtn);
        settingBtn = view.findViewById(R.id.profile_settingBtn);


        editBtn.setOnClickListener(new View.OnClickListener() {
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

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu = new PopupMenu(getActivity(),view);
                popupMenu.getMenuInflater().inflate(R.menu.option_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.option_menu1){
                            LogOut(0);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        settingProfile();
    }

    private void settingProfile() {
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.getProfile().enqueue(new Callback<List<MemberModel>>() {
            @Override
            public void onResponse(Call<List<MemberModel>> call, Response<List<MemberModel>> response) {
                assert response.body() != null;
                MemberModel res = response.body().get(0);

                loginBtn.setVisibility(View.GONE);

                editBtn.setVisibility(View.VISIBLE);
                settingBtn.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);

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
                editBtn.setVisibility(View.INVISIBLE);
                settingBtn.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void GetBoardList() {
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

    @Override
    public void onRefresh() {
        GetBoardList();
        loading.setRefreshing(false);
    }

    private void LogOut(int destroy){
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.logOut(destroy).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                MemberModel res = response.body();

                if(res.getSuccess() == "true")
                {
                    Logout_History(res.getUser_id(), res.getToken_value());
                    Log.d(TAG, "logout user_id, token : " + +res.getUser_id() + res.getToken_value());
                    SaveSharedPreference.clear();
                    Log.d(TAG, "로그아웃");
                    Toast.makeText(getActivity(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                    settingProfile();
                    profile_photoIv.setImageDrawable(getResources().getDrawable(user));
                }
                else if (res.getSuccess() == "false" && destroy == 1)
                {
                    Log.d(TAG, "자동로그인");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "LogOut : " + t.getMessage());
            }
        });
    }

    private void Logout_History(int user_id, String token_value){
        MemberAPI memberAPI = ApiClient.getNoHeaderApiClient().create(MemberAPI.class);
        memberAPI.update_logout_history(user_id, token_value).enqueue(new Callback<MemberModel>()
        {
            @Override
            public void onResponse(@NonNull Call<MemberModel> call, @NonNull retrofit2.Response<MemberModel> response) {
                Log.d(TAG, "Logout_History onResponse: " + token_value);
            }

            @Override
            public void onFailure(@NonNull Call<MemberModel> call, @NonNull Throwable t) {
                Log.e(TAG, "Logout_History onFailure: " + t.getMessage() + user_id + "  " + token_value);
            }
        });
    }
}