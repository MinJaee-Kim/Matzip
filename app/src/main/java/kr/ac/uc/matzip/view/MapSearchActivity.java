package kr.ac.uc.matzip.view;

import static kr.ac.uc.matzip.view.FileUtils.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class MapSearchActivity extends AppCompatActivity {
    private ArrayList<KakaoModel.Document> arrayList;
    private MapSearchAdapter mapSearchAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private EditText sf_contEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_fragment);

        mRecyclerView = (RecyclerView)findViewById(R.id.sf_RV);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        sf_contEt = findViewById(R.id.sf_contEt);

        //Edittext 포커스
        sf_contEt.requestFocus();

        //키보드 올리기
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        sf_contEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + s.toString());

                if(!s.toString().equals("")) {
                    SearchLocation(s.toString());
                }
            }
        });
    }

    private void SearchLocation(String query){
        KakaoAPI kakaoAPI = ApiClient.kakaoSearchApiClient().create(KakaoAPI.class);
        kakaoAPI.searchAddressList("KakaoAK 6bcf1f1f97b9ae55f0878c7378b29037", query).enqueue(new Callback<KakaoModel>() {
            @Override
            public void onResponse(Call<KakaoModel> call, Response<KakaoModel> response) {
                KakaoModel.Meta res = response.body().getMeta();

                if(res.getTotal_count() != 0) {
                    List<KakaoModel.Document> documents = response.body().getDocuments();

                    arrayList = new ArrayList<>();

                    mapSearchAdapter = new MapSearchAdapter(MapSearchActivity.this, arrayList);

//                    Log.d(ContentValues.TAG, "GetBoardList onResponse: " + res.get(0).getAddress_name());

                    for (int i = 0; i < documents.size(); ++i) {
                        arrayList.add(documents.get(i));
                    }

                    mRecyclerView.setAdapter(mapSearchAdapter);

                    Log.d(TAG, "onResponse: " + documents);
                }
            }

            @Override
            public void onFailure(Call<KakaoModel> call, Throwable t) {
                Log.e(TAG, "onResponse : " + t.getMessage());
            }
        });
    }

    public void goBack(View view) {
        finish();

        //키보드 내리기
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
