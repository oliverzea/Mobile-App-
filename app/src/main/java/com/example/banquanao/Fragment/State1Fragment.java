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
import com.example.banquanao.Adapter.State1Adapter;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Order;

import java.util.ArrayList;
import java.util.List;


public class State1Fragment extends Fragment {
    RecyclerView recyclerView ;
    List<Order> mlist;
    State1Adapter adapterState1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_state1, container, false);
        Log.e("s","oncreaview");
        addControl(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mlist.size()>0){
            mlist.clear();
            getData();
            adapterState1 = new State1Adapter(mlist,getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setAdapter(adapterState1);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        adapterState1.notifyDataSetChanged();
    }

    private void addControl(View view) {
        recyclerView=view.findViewById(R.id.rclState1);
        mlist= new ArrayList<>();
        getData();
        adapterState1 = new State1Adapter(mlist,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(adapterState1);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getData() {

        String query = "\n" +
                "select * \n" +
                "from Orders o , Customer c \n" +
                "where o.id_Cus = c.id_Customer and id_State =1";
        Cursor c = PageMainLoginActivity.database.rawQuery(query,null);
        while (c.moveToNext()){
            String name =c.getString(10);
            int number_phone=c.getInt(13);
            int idCard = c.getInt(15);
            int id_Order=c.getInt(0);
            int id_Cus=c.getInt(1);
            int id_Manager=c.getInt(2);
            int id_Staff=c.getInt(3);
            double price_total=c.getDouble(4);
            int id_State=c.getInt(5);
            String toPay=c.getString(6);
            String address= c.getString(14);
            String email=c.getString(12);
            Customer customer=new Customer(id_Cus,name,email,number_phone,address,idCard);
            mlist.add(new Order(id_Order,customer,id_State,id_Manager,id_Staff,price_total,toPay));
        }

        c.close();
    }
}