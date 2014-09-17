package com.tunebrains.infiniteviewpager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tunebrains.views.InfiniteViewPager;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        InfiniteViewPager viewPager = (InfiniteViewPager) findViewById(R.id.view_pager);
        initAdapter(viewPager);
    }

    private void initAdapter(InfiniteViewPager viewPager) {
        viewPager.setAdapter(new PagerAdapter(getFragmentManager()));
    }
    private static class PagerAdapter extends FragmentPagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ItemFragment.getIntance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
    public static class ItemFragment extends Fragment{
        private int mPosition;
        public static ItemFragment getIntance(int pPosition){
            Bundle arg = new Bundle();
            arg.putInt("extra_position",pPosition);
            ItemFragment fr = new ItemFragment();
            fr.setArguments(arg);
            return fr;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPosition = getArguments().getInt("extra_position");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View content = inflater.inflate(R.layout.fragment_item,container,false);
            TextView title = (TextView) content.findViewById(android.R.id.text1);
            title.setText(String.format("%d",mPosition));
            return content;
        }
    }
}
