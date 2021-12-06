package kr.ac.uc.matzip.view;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.model.LocationModel;
import kr.ac.uc.matzip.model.PhotoModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.BoardAPI;
import kr.ac.uc.matzip.presenter.LocationAPI;
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

    static final int REQUEST_IMAGE_ALBUM = 2; //앨범

    Permission permission = new Permission(this);

    private EditText bo_title, bo_cont, bo_address;
    private Button btn_board, btn_map;
    ArrayList<Uri> uriList = new ArrayList<>();     // 이미지의 uri를 담을 ArrayList 객체

    Double latitude, longitude;

    RecyclerView recyclerView;  // 이미지를 보여줄 리사이클러뷰
    ImageView imageView, photo_Iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_board_design);

        bo_title = (EditText) findViewById(R.id.cb_titleEt);
        bo_cont = (EditText) findViewById(R.id.sf_contEt);

        btn_board = (Button) findViewById(R.id.cb_checkBtn);
        photo_Iv = findViewById(R.id.cb_photoIv);
        btn_map = (Button) findViewById(R.id.cb_locationBtn);

//        recyclerView = findViewById(R.id.bo_RV);    //삭제
//        imageView = findViewById(R.id.bo_Iv);
        bo_address = (EditText) findViewById(R.id.cb_locationEt);


        btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bo_title.getText().toString().equals("") && !bo_cont.getText().toString().equals("") && uriList.size() != 0) {
                    postBoard(uriList);
                } else if(uriList.size() == 0){
                    Toast.makeText(getApplicationContext(),"사진을 올려주세요",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"제목과 내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "onClick: " + latitude + longitude);
            }
        });

        photo_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.checkCamera();
                uriList.clear();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_ALBUM);
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

                uploadChat(list, res.getBoard_id());

                if(response.isSuccessful() && res.getSuccess() == "true")
                {
                    Log.d(TAG, "postBoard : 작성한 글 번호" + res.getBoard_id());
                    Toast.makeText(getApplicationContext(),"글 작성에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    BoardActivity.this.finish();
                }
                else
                {
                    Intent intent = new Intent(BoardActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BoardModel> call, @NonNull Throwable t) {
                Log.e(TAG, "postBoard onFailure: " + t.getMessage());
                Intent intent = new Intent(BoardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void uploadChat(ArrayList<Uri> list, int board_id) {
        Log.d(TAG, "uploadChat: 저장할 사진 갯수" + list.size());
        for (int i = 0; i < list.size(); ++i) {
            Uri uri = list.get(i);
            File file = FileUtils.getFile(this, uri);

            if (!file.exists()){
                file.mkdir();
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
            int finalI = i;
            photoAPI.uploadPhoto(filePart, i, board_id).enqueue(new Callback<PhotoModel>() {
                @Override
                public void onResponse(Call<PhotoModel> call, Response<PhotoModel> response) {
                    PhotoModel res = response.body();
                    upLoadChatDB(board_id, res.getPhoto_uri(), finalI);
                    //TODO
                    upLoadLocationDB(board_id, latitude, longitude);
                    Log.e(TAG, "uploadChat onResponse: 성공 : " + res);
                }

                @Override
                public void onFailure(Call<PhotoModel> call, Throwable t) {
                    Log.e(TAG, "uploadChat onFailure: 실패" + t.getMessage());
                }
            });
        }
    }

    private void upLoadChatDB(Integer bo_id, String uri, int index) {
            PhotoAPI photoAPI = ApiClient.getApiClient().create(PhotoAPI.class);
            photoAPI.uploadDB(bo_id, uri, index).enqueue(new Callback<PhotoModel>() {
                @Override
                public void onResponse(Call<PhotoModel> call, Response<PhotoModel> response) {
                    PhotoModel res = response.body();
                    Log.d(TAG, "upLoadChatDB onResponse: " + res.getPhoto_uri());
                }

                @Override
                public void onFailure(Call<PhotoModel> call, Throwable t) {
                    Log.d(TAG, "upLoadChatDB onFailure: " + t.getMessage());
                }
            });
    }

    private void upLoadLocationDB(Integer bo_id, Double latitude, Double longitude) {
        LocationAPI locationAPI = ApiClient.getApiClient().create(LocationAPI.class);
        locationAPI.postData(bo_id, latitude, longitude).enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {
                LocationModel res = response.body();
                Log.d(TAG, "locationDB onResponse: " + res.getSuccess());
            }

            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {
                Log.e(TAG, "locationDB onFailure: " + t.getMessage());
            }
        });
    }
  
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if(data == null){   // 어떤 이미지도 선택하지 않은 경우
                Toast.makeText(getApplicationContext(), "위치를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            } else {
                latitude = data.getDoubleExtra("위도", 0);
                longitude = data.getDoubleExtra("경도", 0);
                String mapAddress = data.getStringExtra("위치");
                Log.d(TAG, "onActivityResult: " + mapAddress);
                bo_address.setText(mapAddress);
            }

        }

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {  //카메라 코드
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            ((ImageView)findViewById(R.id.bo_Iv)).setImageBitmap(imageBitmap);
//        }

        if(requestCode == REQUEST_IMAGE_ALBUM){
            if(data == null){   // 어떤 이미지도 선택하지 않은 경우
                Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
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

                        Picasso.get().load(uriList.get(0)).into(photo_Iv);
                    }
                }
            }
        }
    }

    public void goBack(View view) {
        this.finish();
    }
}
