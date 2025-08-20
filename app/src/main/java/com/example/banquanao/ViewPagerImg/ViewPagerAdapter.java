package com.example.banquanao.ViewPagerImg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.PhotoViewHolder>{

    List<ImageViewPager> mlist;

    public ViewPagerAdapter(List<ImageViewPager> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_viewpager,parent,false);

        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        ImageViewPager imageViewPager = mlist.get(position);
        holder.img.setImageResource(imageViewPager.getResourseId());
    }

    @Override
    public int getItemCount() {
        if(mlist!=null)
            return mlist.size();
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgViewPager);
        }
    }
}
