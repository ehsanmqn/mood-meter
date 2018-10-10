package com.example.ehsan.moodmeter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ehsan on 03/11/16.
 */
public class CustomAdapter extends PagerAdapter {

    Context context;

    int[] imageId = {R.drawable.h1,
                     R.drawable.h2,
                     R.drawable.h3,
                     R.drawable.h4,
                     R.drawable.h5,
                     R.drawable.h6,
                     R.drawable.h7};

    public CustomAdapter(Context context){
        this.context = context;

    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        MemoryCache memoryCache = new MemoryCache();

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.image_item, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.helpImageView);

        try{
            imageView.setImageResource(imageId[position]);
        }catch (Throwable ex){
            ex.printStackTrace();

            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }

        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub

        return view == ((View)object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}
