package kr.ac.uc.matzip.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerLayoutAdapter extends FragmentStateAdapter {

    private MapFragment mMapFragment;
    private BoardListFragment mBoardListFragment;
    private AddMapToBoardFragment mAddMapToBoardFragment;
    private SettingFragment mSettingFragment;

    public ViewPagerLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

        mMapFragment = new MapFragment();
        mBoardListFragment = new BoardListFragment();
        mAddMapToBoardFragment = new AddMapToBoardFragment();
        mSettingFragment = new SettingFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {  //프래그먼트 교체를 보여주는 처리
        switch(position){
            case 1 :
                return mBoardListFragment;
            case 2:
                return mAddMapToBoardFragment;
            case 3:
                return mSettingFragment;
        }
        return mMapFragment;
    }

    @Override
    public int getItemCount() {     // Fragment의 개수
        return 4;
    }
}
