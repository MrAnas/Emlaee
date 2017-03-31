package com.app.emlaee.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.app.emlaee.R;

import java.util.Arrays;


public class SplashPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    Integer list_img[];
    public Boolean isShown = false;



    public SplashPagerAdapter(Context context, Integer imags[]) {
        mContext = context;
        this.list_img = imags;

        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemPosition(Object object) {
        if (Arrays.asList(list_img).contains((View) object)) {
            return Arrays.asList(list_img).indexOf((View) object);
        } else {
            return POSITION_NONE;
        }
    }

    @Override
    public int getCount() {
        return list_img.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container,
                false);
        ImageView imageView = null;
//        com.techeclat.boblingen.Views.AppButton mSkipBtn = null;
        if (itemView != null) {
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            mSkipBtn = (com.techeclat.boblingen.Views.AppButton) itemView.findViewById(R.id.skip_btn);
            imageView.setTag(position);
//            mSkipBtn.setTag(position);
        }
        if (imageView != null) {
//            imgloader.displayImage(list_img.get(position), imageView, options);

            imageView.setImageResource(list_img[position]);
        }

//        if(isShown == true){
//            mSkipBtn.setVisibility(View.VISIBLE);
//        }else{
//            mSkipBtn.setVisibility(View.GONE);
//        }

//        mSkipBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(editor!=null){
//                    editor.putInt(Constants.VIEWPAGER_BIT, 1);
//                    editor.commit();
//                }
//
//                Intent loginIntent = new Intent(mContext, LoginActivity.class);
//                mContext.startActivity(loginIntent);
//                ((Activity)mContext).finish();
//            }
//        });




        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }


    public void ShowSkipButton(Boolean value){
        isShown= value;
        notifyDataSetChanged();
    }

}
