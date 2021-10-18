package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.model.BoardModel;
import kr.ac.uc.matzip.view.MainActivity;

public class BoardPresenter implements BoardModel.Presenter {
    BoardModel.View view;
    BoardPresenter(BoardModel.View view){
        this.view = view;
    }



}
