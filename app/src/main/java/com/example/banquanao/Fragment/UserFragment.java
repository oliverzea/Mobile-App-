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
import com.example.banquanao.Adapter.UserAdapter;
import com.example.banquanao.database.Customer;

import java.util.ArrayList;

public class UserFragment extends Fragment {


    RecyclerView recyclerView;
    UserAdapter adapter;

    private ArrayList<Customer> listCus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        addControl(view);
        return view;
    }
    public void addControl(View view){
        recyclerView=view.findViewById(R.id.RecyclerViewC);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listCus = new ArrayList<>();
        getData();
        adapter= new UserAdapter(this,listCus);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getData() {
        String query ="select * from Customer";// where idProduct = ?;
        Cursor c= PageMainLoginActivity.database.rawQuery(query,null);
        c.moveToFirst();
        while (c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            String pass = c.getString(2);
            String email = c.getString(3);
            int phone =c.getInt(4);
            String address = c.getString(5);
            int idCard = c.getInt(6);
            Customer customer = new Customer(id,name,pass,email,phone,address,idCard);
            listCus.add(customer);

        }
        if (listCus.size() > 0) {
            Log.e("h",listCus.get(0).getName_Cus());

        }else{
            Log.e("h","mang null");
        }
    }
}