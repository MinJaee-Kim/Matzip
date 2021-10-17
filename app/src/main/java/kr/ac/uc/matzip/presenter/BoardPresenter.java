package kr.ac.uc.matzip.presenter;

import kr.ac.uc.matzip.view.MainActivity;

public class BoardPresenter{
    private MainActivity mainActivity;
    BoardPresenter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void onDestroy(){
        mainActivity = null;
    }

}
