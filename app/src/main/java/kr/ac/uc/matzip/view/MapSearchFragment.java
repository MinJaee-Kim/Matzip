package kr.ac.uc.matzip.view;

import static kr.ac.uc.matzip.view.FileUtils.TAG;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.KakaoModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.KakaoAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapSearchFragment extends androidx.fragment.app.Fragment {

    private ArrayList<KakaoModel> arrayList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private View view;

    public static MapSearchFragment newInstance() {
        MapSearchFragment mapSearchFragment = new MapSearchFragment();
        return mapSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.board_layout, container, false);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    private void SearchLocation(String query){
        KakaoAPI kakaoAPI = ApiClient.kakaoSearchApiClient().create(KakaoAPI.class);
        kakaoAPI.searchAddressList("KakaoAK 6bcf1f1f97b9ae55f0878c7378b29037", query).enqueue(new Callback<List<KakaoModel>>() {
            @Override
            public void onResponse(Call<List<KakaoModel>> call, Response<List<KakaoModel>> response) {
                List<KakaoModel> res = response.body();

                arrayList = new ArrayList<>();

                mBoardListAdapter = new BoardListAdapter(getContext(),arrayList);

                for(int i = 0; i < res.size(); ++i)
                {
                    Log.d(ContentValues.TAG, "GetBoardList onResponse: " + res.get(i).getMessage());
                    arrayList.add(res.get(i));
                }

                mRecyclerView.setAdapter(mBoardListAdapter);

                Log.d(TAG, "onResponse: " + res);
            }

            @Override
            public void onFailure(Call<List<KakaoModel>> call, Throwable t) {
                Log.e(TAG, "onResponse : " + t.getMessage());
            }
        });
        Log.d(TAG, "onCreate: ");
    }
}
