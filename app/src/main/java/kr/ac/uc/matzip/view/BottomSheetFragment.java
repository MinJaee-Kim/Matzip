package kr.ac.uc.matzip.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import kr.ac.uc.matzip.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    Context context;

    public BottomSheetFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        Button btnOK = view.findViewById(R.id.btn_Ok);
        BottomSheetBehavior bottomSheetBehavior = new BottomSheetBehavior();    //여기의 메서드를 사용해서 크기를 조절

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "확인", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        Button btnClose = view.findViewById(R.id.btn_Close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "닫기", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return view;
    }



}
