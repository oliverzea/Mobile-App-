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

import com.example.banquanao.Activity.PageStaffActivity;
import com.example.banquanao.R;
import com.example.banquanao.Adapter.AdapterOrder;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    RecyclerView recyclerOrder;
    View view;

    List<Order> mlistOrder;
    AdapterOrder adapterOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("s","On create view order");
        view = inflater.inflate(R.layout.fragment_order, container, false);
        addControl(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mlistOrder.size()>0){
            mlistOrder.clear();
            loadDataState();
            adapterOrder = new AdapterOrder(mlistOrder,getContext());
            recyclerOrder.setAdapter(adapterOrder);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

            recyclerOrder.setLayoutManager(linearLayoutManager);
        }
        adapterOrder.notifyDataSetChanged();
    }

    private void loadDataState(){
        String query ="select * from Orders o , Customer c where o.id_Cus = c.id_Customer and id_State=2";
        Cursor c= PageStaffActivity.database.rawQuery(query,null);
        mlistOrder.clear();
        while(c.moveToNext()){
            int id_Order = c.getInt(0);
            int id_Cus = c.getInt(1);
            int id_Manager = c.getInt(2);
            int id_Staff = c.getInt(3);
            double price_total = c.getDouble(4);
            int id_State = c.getInt(5);
            String toPay = c.getString(6);
            Log.e("s",id_Order+"");
            Customer cus = new Customer();
            cus.setId_cus(id_Cus);
            cus.setName_Cus(c.getString(10));
            cus.setEmail(c.getString(12));
            cus.setNumberPhone(c.getInt(13));
            mlistOrder.add(new Order(cus, id_Order,id_Manager,id_Staff,price_total,id_State, toPay));

        }
        Log.e("g",mlistOrder.size()+"day la order fragment");
        c.close();

    }

    private void addControl(View view) {
        recyclerOrder = view.findViewById(R.id.recyclerOrder);

        mlistOrder = new ArrayList<>();
        loadDataState();
        adapterOrder = new AdapterOrder(mlistOrder,getContext());
        recyclerOrder.setAdapter(adapterOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerOrder.setLayoutManager(linearLayoutManager);
    }

}