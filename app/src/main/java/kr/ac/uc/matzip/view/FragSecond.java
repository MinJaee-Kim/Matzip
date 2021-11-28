package kr.ac.uc.matzip.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kr.ac.uc.matzip.R;

public class FragSecond extends androidx.fragment.app.Fragment {
    private View view;

    public static FragSecond newInstance() {
        FragSecond fragSecond = new FragSecond();
        return fragSecond;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_second, container, false);

        return view;
    }
}
