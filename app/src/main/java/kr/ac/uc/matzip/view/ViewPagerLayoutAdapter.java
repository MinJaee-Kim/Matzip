package kr.ac.uc.matzip.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerLayoutAdapter extends FragmentStateAdapter {
    private MapFragment mMapFragment;
    public ViewPagerLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

        mMapFragment = new MapFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {  //프래그먼트 교체를 보여주는 처리
        switch(position){
            case 1 :
                return new BoardListFragment();
            case 2:
                return new SettingFragment();
        }
        return mMapFragment;
    }

    @Override
    public int getItemCount() {     // Fragment의 개수
        return 3;
    }
}
