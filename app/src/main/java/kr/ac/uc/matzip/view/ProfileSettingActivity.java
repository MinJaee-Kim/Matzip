package kr.ac.uc.matzip.view;

import static android.content.ContentValues.TAG;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import kr.ac.uc.matzip.R;
import kr.ac.uc.matzip.model.MemberModel;
import kr.ac.uc.matzip.presenter.ApiClient;
import kr.ac.uc.matzip.presenter.MemberAPI;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSettingActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_ALBUM = 2;

    private Button edit_settingBtn,edit_checkBtn;
    private ImageView edit_photoIv;
    private TextView edit_status_message;
    private Uri profileUri;

    Permission permission = new Permission(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_design);

        edit_settingBtn = findViewById(R.id.edit_settingBtn);
        edit_checkBtn = findViewById(R.id.edit_checkBtn);
        edit_photoIv = findViewById(R.id.edit_photoIv);
        edit_status_message = findViewById(R.id.edit_status_messageEt);

        settingProfile();

        edit_checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProfile(profileUri);
            }
        });
    }

    private void settingProfile() {
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.getProfile().enqueue(new Callback<List<MemberModel>>() {
            @Override
            public void onResponse(Call<List<MemberModel>> call, Response<List<MemberModel>> response) {
                assert response.body() != null;
                MemberModel res = response.body().get(0);

                Log.d(TAG, "settingProfile onResponse: " + res);

                edit_status_message.setText(res.getStatus_message());

                if(res.getUser_photo_uri() != null)
                {
                    Glide.with(ProfileSettingActivity.this).load(res.getUser_photo_uri()).into(edit_photoIv);
                }
            }

            @Override
            public void onFailure(Call<List<MemberModel>> call, Throwable t) {
                Log.e(TAG, "settingProfile onFailure: " + t.getMessage());
            }
        });
    }

    private void uploadProfile(Uri photoUri) {
        Log.d(TAG, "uploadProfile: " + photoUri);
        File file = FileUtils.getFile(this, photoUri);

        if (!file.exists()){
            file.mkdir();
        }

        String fileName = file.getName();

        // Uri 타입의 파일경로를 가지는 RequestBody 객체 생성
        RequestBody fileBody = RequestBody.create(MediaType.parse(getContentResolver().getType(photoUri)), file);

        // 사진 파일 이름
        // RequestBody로 Multipart.Part 객체 생성
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("uploaded_file", fileName, fileBody);
        Log.d(TAG, "uploadProfile: " + filePart);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("프로필을 수정하시겠습니까?");
        builder.setMessage("");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
                        memberAPI.uploadProfile(filePart).enqueue(new Callback<MemberModel>() {
                            @Override
                            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                                String res = response.body().getMessage();
                                updateUserProfileDB(response.body().getUser_photo_uri());
                                Log.d(TAG, "uploadProfile onResponse: 성공 : " + response.body().getUser_photo_uri());
                            }

                            @Override
                            public void onFailure(Call<MemberModel> call, Throwable t) {
                                Log.e(TAG, "uploadProfile onFailure: 실패" + t.getMessage());
                            }
                        });
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    private void updateUserProfileDB(String uri) {
        MemberAPI memberAPI = ApiClient.getApiClient().create(MemberAPI.class);
        memberAPI.user_profile_update(uri).enqueue(new Callback<MemberModel>() {
            @Override
            public void onResponse(Call<MemberModel> call, Response<MemberModel> response) {
                MemberModel res = response.body();
                Log.d(TAG, "updateUserProfileDB onResponse: " + res.getUser_photo_uri());

                if(response.isSuccessful() && res.getSuccess() == "true")
                {
                    Log.d(TAG, "updateUserProfileDB : 업데이트한 유저" + res.getUser_id());
                    Toast.makeText(getApplicationContext(),"프로필 업로드를 성공하였습니다.",Toast.LENGTH_SHORT).show();
                    ProfileSettingActivity.this.finish();
                }
                else
                    {
                        Intent intent = new Intent(ProfileSettingActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
            }

            @Override
            public void onFailure(Call<MemberModel> call, Throwable t) {
                Log.d(TAG, "updateUserProfileDB onFailure: " + t.getMessage());
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + requestCode);

        if(requestCode == REQUEST_IMAGE_ALBUM){
            if(data == null){   // 어떤 이미지도 선택하지 않은 경우
                Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            }
            else if(data.getClipData() == null) {   // 이미지를 하나라도 선택한 경우
                // 이미지를 하나만 선택한 경우
                Log.e("single choice: ", String.valueOf(data.getData()));
                profileUri = data.getData();
            }
            else {
                ClipData clipData = data.getClipData();
                profileUri = clipData.getItemAt(0).getUri();
            }
//          Glide.with(this).load(profileUri).into(edit_photoIv);
            Picasso.get().load(profileUri).into(edit_photoIv);
        }
    }

    public void setPhoto(View view) {
        permission.checkCamera();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_ALBUM);
    }

    public void goBack(View view) {
        finish();
    }
}
