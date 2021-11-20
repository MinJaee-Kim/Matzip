package kr.ac.uc.matzip.view;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import kr.ac.uc.matzip.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private Button bs_photo, bs_mkboard;
    private TextView bs_address;
    private EditText bs_title, bs_cont;
    private View contentView;
    private BottomSheetBehavior mBehavior;

    ImageView imageView;
    Context context;

    public BottomSheetFragment(Context context) {
        this.context = context;
    }

    @Override   //여기서 dialog를 생성합니다.
    public void setupDialog(@NonNull Dialog dialog, int style) {    //onCreateView 이전에 불립니다.
        contentView = View.inflate(getContext(), R.layout.bottom_sheet, null);
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
        bs_address.setText(getArguments().getString(AddMapToBoardActivity.ADDRESS_VALUE));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        bs_photo = view.findViewById(R.id.bs_btnPhoto);
        bs_mkboard = view.findViewById(R.id.bs_mkBtn);
        bs_address = view.findViewById(R.id.bs_addressTv);
        bs_title = view.findViewById(R.id.bs_titleEt);
        bs_cont = view.findViewById(R.id.bs_contEt);
        imageView = view.findViewById(R.id.bs_photoIv);



        bs_mkboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "확인", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return view;
    }



}
