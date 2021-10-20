package kr.ac.uc.matzip.presenter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.model.RegisterModel;

public class RegisterPresenter implements RegisterModel.Presenter {
    RegisterModel.View view;
    RegisterPresenter(RegisterModel.View view){
        this.view = view;
    }

}
