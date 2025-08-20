package com.example.banquanao.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.Activity.PageMainLoginActivity;
import com.example.banquanao.R;
import com.example.banquanao.Activity.HomeActivity;
import com.example.banquanao.Adapter.StaffAdapter;
import com.example.banquanao.database.Staff;

import java.util.ArrayList;


public class StaffFragment extends Fragment {

    RecyclerView recyclerView;
    StaffAdapter adapter;
    private ArrayList<Staff> listStaff;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        addControl(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void addControl(View view) {
        recyclerView=view.findViewById(R.id.RecyclerViewS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listStaff = new ArrayList<>();
        getData();
        adapter= new StaffAdapter(this,listStaff);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getData() {
        String query ="select * from Staff";// where idProduct = ?;
        Cursor c= PageMainLoginActivity.database.rawQuery(query,null);
        c.moveToFirst();
        while (c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            String pass = c.getString(3);
            int phone =c.getInt(4);
            String email = c.getString(5);
            int idMana = c.getInt(6);
            byte[] image = c.getBlob(7);
            Staff staff=new Staff(id,name,pass,phone,email,image,idMana);
            listStaff.add(staff);

        }
        if (listStaff.size() > 0) {
            Log.e("h",listStaff.get(0).getName());

        }else{
            Log.e("h","mang null");
        }
    }
}