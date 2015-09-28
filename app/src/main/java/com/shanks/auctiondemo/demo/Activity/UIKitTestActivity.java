package com.shanks.auctiondemo.demo.Activity;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paimai.auctiondemo.R;
import com.taobao.uikit.component.Banner;

import java.util.ArrayList;

public class UIKitTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uikit_test);

        Banner banner = (Banner) findViewById(R.id.banner);

        ArrayList<View> viewList = new ArrayList<View>();

        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(R.drawable.mm_1);
        viewList.add(iv);
        iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(R.drawable.mm_2);
        viewList.add(iv);
        iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(R.drawable.mm_3);
        viewList.add(iv);
        iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(R.drawable.mm_4);
        viewList.add(iv);


        banner.setScrollInterval(1500);
        SimpleAdapter adapter = new SimpleAdapter(viewList);
        banner.setAdapter(adapter);
    }

    class SimpleAdapter extends PagerAdapter
    {

        private ArrayList<View> mViewList = new ArrayList<View>();

        public SimpleAdapter(ArrayList<View> list)
        {
            mViewList = list;
        }

        @Override
        public int getCount()
        {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position)
        {
            View view = mViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
}
