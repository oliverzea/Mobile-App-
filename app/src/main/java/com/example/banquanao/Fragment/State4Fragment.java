package com.example.banquanao.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.Activity.PageMainLoginActivity;
import com.example.banquanao.Adapter.State3Adapter;
import com.example.banquanao.Adapter.State4Adatapter;
import com.example.banquanao.R;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Order;

import java.util.ArrayList;
import java.util.List;

public class State4Fragment extends Fragment {

    RecyclerView recyclerView;
    State4Adatapter adapter;
    List<Order> mlis;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_state4, container, false);
        addControls(view);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        if(mlis.size()>0){
            mlis.clear();
            getData();
            adapter = new State4Adatapter(mlis,getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        adapter.notifyDataSetChanged();
    }
    private void addControls(View view) {
        recyclerView= view.findViewById(R.id.rclState4);
        mlis= new ArrayList<>();
        getData();
        adapter= new State4Adatapter(mlis,getContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getData() {

        String query = "\n" +
                "select * \n" +
                "from Orders o , Customer c \n" +
                "where o.id_Cus = c.id_Customer and id_State =4";
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
            mlis.add(new Order(id_Order,customer,id_State,id_Manager,id_Staff,price_total,toPay));
        }

        c.close();
    }
}
