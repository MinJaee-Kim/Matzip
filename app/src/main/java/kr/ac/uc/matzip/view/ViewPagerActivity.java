package kr.ac.uc.matzip.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import kr.ac.uc.matzip.R;

public class ViewPagerActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 pager2;
    ViewPagerLayoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_activity);

        /*뷰페이저*/
        pager2 = findViewById(R.id.vp_pagerVp);
        tabLayout = findViewById(R.id.vp_tabLayout);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new ViewPagerLayoutAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);
        
        //스와이프 막기
        pager2.setUserInputEnabled(false);

        //tabLayout
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_location_on));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_settings));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

}
