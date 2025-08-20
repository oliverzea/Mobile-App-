package com.example.banquanao.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.banquanao.Fragment.DeliveredFragment;
import com.example.banquanao.Fragment.EmployeeFragment;
import com.example.banquanao.Fragment.OrderFragment;
import com.example.banquanao.Fragment.SuccessFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
//            case 0:
//                return new HomeFragment();
            case 1:
                return new EmployeeFragment();
            case 2:
                return new OrderFragment();
            case 3:
                return new DeliveredFragment();
            case 4:
                return new SuccessFragment();
            default:
                return new OrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
