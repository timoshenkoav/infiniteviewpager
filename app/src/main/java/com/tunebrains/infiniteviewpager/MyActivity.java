package com.tunebrains.infiniteviewpager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tunebrains.views.InfiniteViewPager;
import com.tunebrains.views.ParallaxPagerTransformer;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        InfiniteViewPager viewPager = (InfiniteViewPager) findViewById(R.id.view_pager);
        viewPager.setPageTransformer(false,new ParallaxPagerTransformer(R.id.background));
        initAdapter(viewPager);
    }

    private void initAdapter(InfiniteViewPager viewPager) {
        viewPager.setAdapter(new PagerAdapter(getFragmentManager(),viewPager));
    }
    private static class PagerAdapter extends InfiniteViewPager.PagerAdapterWrapper {

        public PagerAdapter(FragmentManager fm,InfiniteViewPager viewPager) {
            super(fm,viewPager);
        }


        @Override
        protected Fragment getItemWrapped(int positionForAdapter) {

            return ItemFragment.getIntance(positionForAdapter);
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

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("position",mPosition);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View content = inflater.inflate(R.layout.fragment_item,container,false);
            if (savedInstanceState!=null){
                mPosition = savedInstanceState.getInt("position");
            }
            final ImageView image = (ImageView) content.findViewById(R.id.background);
            image.setImageResource(R.drawable.option);
            image.post(new Runnable() {
                @Override
                public void run() {
                    Matrix matrix = new Matrix();
                    matrix.reset();

                    float wv = image.getWidth();
                    float hv = image.getHeight();

                    float wi = image.getDrawable().getIntrinsicWidth();
                    float hi = image.getDrawable().getIntrinsicHeight();

                    float width = wv;
                    float height = hv;

                    if (wi / wv > hi / hv) {
                        matrix.setScale(hv / hi, hv / hi);
                        width = wi * hv / hi;
                    } else {
                        matrix.setScale(wv / wi, wv / wi);
                        height = hi * wv / wi;
                    }

                    matrix.preTranslate((wv - width) / 2, (hv - height) / 2);
                    image.setScaleType(ImageView.ScaleType.MATRIX);
                    image.setImageMatrix(matrix);
                }
            });
            TextView title = (TextView) content.findViewById(android.R.id.text1);
            title.setText(String.format("%d",mPosition));
            return content;
        }
    }
}
