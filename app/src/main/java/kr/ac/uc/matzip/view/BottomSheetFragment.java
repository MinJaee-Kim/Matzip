package kr.ac.uc.matzip.view;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import kr.ac.uc.matzip.presenter.PhotoAPI;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private Button bs_addressBtn, bs_mkcheckBtn;
    private EditText bs_titleEt, bs_contEt, bs_addressEt;
    private ImageView bs_photoIv;
    private MultiAutoCompleteTextView bs_hashEt;
    private View contentView;
    private BottomSheetBehavior mBehavior;

    ArrayList<Uri> uriList = new ArrayList<>();     // 이미지의 uri를 담을 ArrayList 객체

    Context context;


    ImageView imageView;

    public BottomSheetFragment(Context context) {
        this.context = context;
    }

    @Override   //여기서 dialog를 생성합니다.
    public void setupDialog(@NonNull Dialog dialog, int style) {    //onCreateView 이전에 불립니다.
        contentView = View.inflate(getContext(), R.layout.bottom_sheet_design, null);
        dialog.setContentView(contentView);
        //여기의 메서드를 사용해서 크기를 조절
        mBehavior = BottomSheetBehavior.from((View) contentView.getParent());

        mBehavior.setSkipCollapsed(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBehavior != null) {
            mBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        }
        Log.d(TAG, "onStart: " + getArguments().getString(AddMapToBoardActivity.ADDRESS_VALUE));
        bs_addressEt.setText(getArguments().getString(AddMapToBoardActivity.ADDRESS_VALUE));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_design, container, false);

        Permission permission = new Permission(getActivity());

        bs_addressBtn = view.findViewById(R.id.bs_locationBtn);
        bs_mkcheckBtn = view.findViewById(R.id.bs_checkBtn);
        bs_titleEt = view.findViewById(R.id.bs_titleEt);
        bs_contEt = view.findViewById(R.id.bs_contEt);
        bs_addressEt = view.findViewById(R.id.bs_locationEt);
        bs_photoIv = view.findViewById(R.id.bs_photoIv);



        bs_mkcheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "확인", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        bs_photoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.checkCamera();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, BoardActivity.REQUEST_IMAGE_ALBUM);
            }
        });

        bs_addressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddBoardToMapActivity.class);
                startActivityForResult(intent, BoardActivity.REQUEST_CODE);

            }
        });

        return view;
    }

    private void postBoard(ArrayList<Uri> list)
    {
        final String title = bs_titleEt.getText().toString();
        final String cont = bs_contEt.getText().toString();

        BoardAPI boardAPI = ApiClient.getApiClient().create(BoardAPI.class);
        boardAPI.postData(title, cont).enqueue(new Callback<BoardModel>()
        {
            @Override
            public void onResponse(@NonNull Call<BoardModel> call, @NonNull Response<BoardModel> response) {
                BoardModel res = response.body();

                Log.d(TAG, "onResponse: " + res.getBoard_id());

                if(response.isSuccessful() && res.getSuccess() == "true")
                {
                    if(list.size() != 0) {
                        uploadChat(list, res.getBoard_id());
                    }
                    Toast.makeText(getActivity(),"글 작성에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BoardModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void uploadChat(ArrayList<Uri> list, int board_id) {
        for (int i = 0; i < list.size(); ++i) {
            Uri uri = list.get(i);
            File file = FileUtils.getFile(getActivity(), uri);

            if (!file.exists()){
                file.mkdir();
                Log.d(TAG, "uploadChat: ");
            }

            String fileName = file.getName();

            // Uri 타입의 파일경로를 가지는 RequestBody 객체 생성
            RequestBody fileBody = RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(uri)), file);

            // 사진 파일 이름
            // RequestBody로 Multipart.Part 객체 생성
            Log.d(TAG, "uploadChat: " + fileBody);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("uploaded_file", fileName, fileBody);
            Log.d(TAG, "uploadChat: " + filePart);

            PhotoAPI photoAPI = ApiClient.getApiClient().create(PhotoAPI.class);
            int finalI = i;
            photoAPI.uploadPhoto(filePart, i, board_id).enqueue(new Callback<BoardModel>() {
                @Override
                public void onResponse(Call<BoardModel> call, Response<BoardModel> response) {
                    BoardModel res = response.body();
                    upLoadChatDB(board_id, res.getPhoto_uri(), finalI);
                    Log.e(TAG, "onResponse: 성공 : " + res);
                }

                @Override
                public void onFailure(Call<BoardModel> call, Throwable t) {
                    Log.e(TAG, "onFailure: 실패" + t.getMessage());
                }
            });
        }
    }

    private void upLoadChatDB(Integer bo_id, String uri, int index) {
        PhotoAPI photoAPI = ApiClient.getApiClient().create(PhotoAPI.class);
        photoAPI.uploadDB(bo_id, uri, index).enqueue(new Callback<BoardModel>() {
            @Override
            public void onResponse(Call<BoardModel> call, Response<BoardModel> response) {
                BoardModel res = response.body();
                Log.d(TAG, "upLoadChatDB onResponse: " + res.getPhoto_uri());
            }

            @Override
            public void onFailure(Call<BoardModel> call, Throwable t) {
                Log.d(TAG, "upLoadChatDB onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BoardActivity.REQUEST_CODE) {
            Double latitude = data.getDoubleExtra("위도", 0);
            Double longitude = data.getDoubleExtra("경도", 0);
            String mapAddress = data.getStringExtra("위치");
            Log.d(TAG, "onActivityResult: " + mapAddress);
            bs_addressEt.setText(mapAddress);

        }

        if(requestCode == BoardActivity.REQUEST_IMAGE_ALBUM){
            if(data == null){   // 어떤 이미지도 선택하지 않은 경우
                Toast.makeText(getActivity(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            }
            else{   // 이미지를 하나라도 선택한 경우
                if(data.getClipData() == null){     // 이미지를 하나만 선택한 경우
                    Log.e("single choice: ", String.valueOf(data.getData()));
                    Uri imageUri = data.getData();
                    uriList.add(imageUri);
                }
                else{      // 이미지를 여러장 선택한 경우
                    ClipData clipData = data.getClipData();
                    Log.e("clipData", String.valueOf(clipData.getItemCount()));

                    if(clipData.getItemCount() > 10){   // 선택한 이미지가 11장 이상인 경우
                        Toast.makeText(getActivity(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    }
                    else{   // 선택한 이미지가 1장 이상 10장 이하인 경우
                        Log.e(TAG, "multiple choice");

                        for (int i = 0; i < clipData.getItemCount(); i++){
                            Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.
                            try {
                                uriList.add(imageUri);  //uri를 list에 담는다.

                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }

                        Picasso.get().load(uriList.get(0)).into(bs_photoIv);
                    }
                }
            }
        }
    }



}
