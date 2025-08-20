package com.example.banquanao.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.banquanao.Fragment.BillFragment;
import com.example.banquanao.Fragment.CartFragment;
import com.example.banquanao.Fragment.HomeFragment;
import com.example.banquanao.Fragment.InforFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    Context mcontext;
    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();

            case 1:
                return new CartFragment();

            case 2:
                return new BillFragment();

            case 3:
                return new InforFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
