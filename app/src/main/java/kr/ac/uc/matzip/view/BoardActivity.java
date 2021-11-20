package kr.ac.uc.matzip.view;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.model.PhotoModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import kr.ac.uc.matzip.presenter.PhotoAPI;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardActivity extends AppCompatActivity {
    private static final String TAG = "BoardActivity";
    public static final int REQUEST_CODE = 3;

    static final int REQUEST_IMAGE_CAPTURE = 1; //카메라
    static final int REQUEST_IMAGE_ALBUM = 2; //앨범

    Permission permission = new Permission(this);

    private TextView bo_address;
    private EditText bo_title, bo_cont;
    private Button btn_board, btn_IV, btn_Camera, btn_map;
    ArrayList<Uri> uriList = new ArrayList<>();     // 이미지의 uri를 담을 ArrayList 객체

    RecyclerView recyclerView;  // 이미지를 보여줄 리사이클러뷰
    ImageView imageView;
    MultiImageAdapter adapter;  // 리사이클러뷰에 적용시킬 어댑터

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_board);

        bo_title = (EditText) findViewById(R.id.bo_title);
        bo_cont = (EditText) findViewById(R.id.bo_cont);

        btn_board = (Button) findViewById(R.id.bo_boBtn);
        btn_IV = (Button) findViewById(R.id.bo_ivBtn);
        btn_Camera = (Button) findViewById(R.id.bo_Camera);
        btn_map = (Button) findViewById(R.id.bo_map);

        recyclerView = findViewById(R.id.bo_RV);
        imageView = findViewById(R.id.bo_Iv);
        bo_address = findViewById(R.id.bo_address);


        btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postBoard(uriList);
            }
        });

        btn_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.checkCamera();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_ALBUM);
            }
        });

        btn_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.checkCamera();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBoardToMapActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    private void postBoard(ArrayList<Uri> list)
    {
        final String title = bo_title.getText().toString();
        final String cont = bo_cont.getText().toString();

        BoardAPI boardAPI = ApiClient.getApiClient().create(BoardAPI.class);
        boardAPI.postData(title, cont).enqueue(new Callback<BoardModel>()
        {
            @Override
            public void onResponse(@NonNull Call<BoardModel> call,@NonNull Response<BoardModel> response) {
                BoardModel res = response.body();

                Log.d(TAG, "onResponse: " + res.getBo_id());

                if(response.isSuccessful() && res.getSuccess() == "true")
                {
                    uploadChat(list, res.getBo_id());
                    Toast.makeText(getApplicationContext(),"글 작성에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(BoardActivity.this, LoginActivity.class);
                    startActivity(intent);
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
            File file = FileUtils.getFile(this, uri);

            if (!file.exists()){
                file.mkdir();
                Log.d(TAG, "uploadChat: asdas");
            }
            
            String fileName = file.getName();

            // Uri 타입의 파일경로를 가지는 RequestBody 객체 생성
            RequestBody fileBody = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);

            // 사진 파일 이름
            // RequestBody로 Multipart.Part 객체 생성
            Log.d(TAG, "uploadChat: " + fileBody);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("uploaded_file", fileName, fileBody);
            Log.d(TAG, "uploadChat: " + filePart);

            PhotoAPI photoAPI = ApiClient.getApiClient().create(PhotoAPI.class);
            photoAPI.uploadPhoto(filePart, i, board_id).enqueue(new Callback<PhotoModel>() {
                @Override
                public void onResponse(Call<PhotoModel> call, Response<PhotoModel> response) {
                    PhotoModel res = response.body();
                    upLoadChatDB(board_id, res.getPhoto_uri());
                    Log.e(TAG, "onResponse: 성공 : " + res);
                }

                @Override
                public void onFailure(Call<PhotoModel> call, Throwable t) {
                    Log.e(TAG, "onFailure: 실패" + t.getMessage());
                }
            });
        }
    }

    private void upLoadChatDB(Integer bo_id, String uri) {
            PhotoAPI photoAPI = ApiClient.getApiClient().create(PhotoAPI.class);
            photoAPI.uploadDB(bo_id, uri).enqueue(new Callback<PhotoModel>() {
                @Override
                public void onResponse(Call<PhotoModel> call, Response<PhotoModel> response) {

                }

                @Override
                public void onFailure(Call<PhotoModel> call, Throwable t) {

                }
            });
    }
  
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            String latitude = data.getStringExtra("위도");
            String longitude = data.getStringExtra("경도");
            String mapAddress = data.getStringExtra("위치");
            bo_address.setText(mapAddress);

        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {  //카메라 코드
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ((ImageView)findViewById(R.id.bo_Iv)).setImageBitmap(imageBitmap);
        }

        if(requestCode == REQUEST_IMAGE_ALBUM){
            if(data == null){   // 어떤 이미지도 선택하지 않은 경우
                Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            }
            else{   // 이미지를 하나라도 선택한 경우
                if(data.getClipData() == null){     // 이미지를 하나만 선택한 경우
                    Log.e("single choice: ", String.valueOf(data.getData()));
                    Uri imageUri = data.getData();
                    uriList.add(imageUri);

                    adapter = new MultiImageAdapter(uriList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
                }
                else{      // 이미지를 여러장 선택한 경우
                    ClipData clipData = data.getClipData();
                    Log.e("clipData", String.valueOf(clipData.getItemCount()));

                    if(clipData.getItemCount() > 10){   // 선택한 이미지가 11장 이상인 경우
                        Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
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

                        adapter = new MultiImageAdapter(uriList, getApplicationContext());
                        recyclerView.setAdapter(adapter);   // 리사이클러뷰에 어댑터 세팅
                        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));     // 리사이클러뷰 수평 스크롤 적용
                    }
                }
            }
        }
    }

}
