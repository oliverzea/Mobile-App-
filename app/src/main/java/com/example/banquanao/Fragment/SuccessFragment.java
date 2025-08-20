package com.example.banquanao.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.Activity.PageStaffActivity;
import com.example.banquanao.R;
import com.example.banquanao.Adapter.AdapterSuccess;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Order;

import java.util.ArrayList;
import java.util.List;

public class SuccessFragment extends Fragment {
    RecyclerView recyclerSuccess;
    List<Order> mlistProSuccess;
    AdapterSuccess adapterSuccess;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_success, container, false);
        addControl(view);
        return view;
    }

    public void onStart() {
        super.onStart();
        if(mlistProSuccess.size()>0){
            mlistProSuccess.clear();
            getDataSuccess();
            adapterSuccess = new AdapterSuccess(mlistProSuccess,getContext());
            recyclerSuccess.setAdapter(adapterSuccess);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

            recyclerSuccess.setLayoutManager(linearLayoutManager);
        }
        adapterSuccess.notifyDataSetChanged();
    }
    private void getDataSuccess(){
        String query ="select * from Orders o , Customer c where o.id_Cus = c.id_Customer and id_State=4";
        Cursor c= PageStaffActivity.database.rawQuery(query,null);
        mlistProSuccess.clear();
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
            mlistProSuccess.add(new Order(cus, id_Order,id_Manager,id_Staff,price_total,id_State, toPay));
        }
        c.close();
    }
    private void addControl(View view) {

        recyclerSuccess = view.findViewById(R.id.recyclerSuccess);
        mlistProSuccess = new ArrayList<>();
        getDataSuccess();
        adapterSuccess = new AdapterSuccess(mlistProSuccess,getContext());
        recyclerSuccess.setAdapter(adapterSuccess);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerSuccess.setLayoutManager(linearLayoutManager);

    }
}
