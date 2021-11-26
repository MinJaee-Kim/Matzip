package kr.ac.uc.matzip.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {  //프래그먼트 교체를 보여주는 처리
        switch(position){
            case 1 :
//                return new FragSecond();
            case 2:
//                return new FragThird();
        }
        return new MapActivity();
    }

    @Override
    public int getItemCount() {     // Fragment의 개수
        return 2;
    }
}
