package com.example.hotelmangementproject.adapters.roomservicesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.hotelmangementproject.R;

import java.util.List;

public class ImageSlideAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> listImage;

    public ImageSlideAdapter(Context mContext, List<String> listImage) {
        this.mContext = mContext;
        this.listImage = listImage;
    }
    public void setData(List<String> listImage){
        this.listImage = listImage;
    }
    @Override
    public int getCount() {
        if(listImage != null){
            return listImage.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.frag_rs_item_photo, container, false);

        ImageView imageView = view.findViewById(R.id.img_selected_available);

        String url = listImage.get(position);
        if(url != null){
            Glide.with(mContext).load(url).into(imageView);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
