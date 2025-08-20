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
import com.example.banquanao.Adapter.State2Adapter;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Order;

import java.util.ArrayList;
import java.util.List;


public class State2Fragment extends Fragment {
    RecyclerView recyclerView;
    State2Adapter adapter;
    List<Order> mlistOrder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state2, container, false);
        addControl(view);
        return view;
    }

    private void addControl(View view) {
        recyclerView = view.findViewById(R.id.rclSate2);
        mlistOrder = new ArrayList<>() ;
        getDataOrder();
        adapter = new State2Adapter(mlistOrder,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getDataOrder() {

        String query = "\n" +
                "select * \n" +
                "from Orders o , Customer c \n" +
                "where o.id_Cus = c.id_Customer and id_State =2";
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
            mlistOrder.add(new Order(id_Order,customer,id_State,id_Manager,id_Staff,price_total,toPay));
        }
        Log.e("s",mlistOrder.get(0).getCustomer().getName_Cus()+" State 2");
        c.close();
    }
}