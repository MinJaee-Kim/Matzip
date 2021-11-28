package kr.ac.uc.matzip.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import kr.ac.uc.matzip.R;

public class ViewPagerActivity extends AppCompatActivity {
    ViewPager2 pager2;
    ViewPagerLayoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_activity);

        /*뷰페이저*/
        pager2 = findViewById(R.id.vp_pagerVp);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new ViewPagerLayoutAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);
        
        //스와이프 막기
        pager2.setUserInputEnabled(false);

        /*뷰페이저*/
    }

}
