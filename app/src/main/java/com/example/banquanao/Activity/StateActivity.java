package com.example.banquanao.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.banquanao.Fragment.State3Fragment;
import com.example.banquanao.Fragment.State4Fragment;
import com.example.banquanao.R;
import com.example.banquanao.Fragment.State1Fragment;
import com.example.banquanao.Fragment.State2Fragment;
import com.google.android.material.tabs.TabLayout;

public class StateActivity extends AppCompatActivity {
    TabLayout tabLayout;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_state);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.selectTab(tabLayout.getTabAt(0));
        toolbar = findViewById(R.id.materialToolbar);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    setFragment(new State1Fragment());
                    Toast.makeText(getApplicationContext(),"State 1",Toast.LENGTH_SHORT).show();
                }

                if (tab.getPosition() == 1) {
                    setFragment(new State2Fragment());
                    Toast.makeText(getApplicationContext(),"State 2",Toast.LENGTH_SHORT).show();

                }
                if (tab.getPosition() == 2) {
                    setFragment(new State3Fragment());
                    Toast.makeText(getApplicationContext(),"State 1",Toast.LENGTH_SHORT).show();
                }

                if (tab.getPosition() == 3) {
                    setFragment(new State4Fragment());
                    Toast.makeText(getApplicationContext(),"State 2",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setFragment(new State1Fragment());
    }

    void setFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}