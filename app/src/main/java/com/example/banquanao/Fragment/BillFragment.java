package com.example.banquanao.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;
import com.example.banquanao.Adapter.BillRecyclerView;
import com.example.banquanao.database.Bill;

import java.util.ArrayList;
import java.util.List;


public class BillFragment extends Fragment {

    RecyclerView recyclerBill;
    List<Bill> listBill;
    BillRecyclerView billRecyclerView;
    int size =0;

    Button btnRenew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        addControls(view);
        addRe();
        billRecyclerView.notifyDataSetChanged();
        btnRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBill.clear();
                String query = "select  o.id_Order , pro.name, od.number, od.price,od.price_total,o.id_State,pro.image1\n" +
                        "from Order_Detail od,Orders o, Product pro, Customer cus\n" +
                        "where od.id_order = o.id_Order and pro.id_product = od.id_Product and o.id_Cus = cus.id_Customer and cus.id_Customer =?";
                Cursor cursor = PageMainCus.database.rawQuery(query,new String[]{String.valueOf(PageMainCus.customer.getId_cus())});
                cursor.moveToFirst();
                while(cursor.isAfterLast()==false){
                    Bill bill = new Bill();
                    bill.setId(cursor.getInt(0));
                    bill.setName(cursor.getString(1));
                    bill.setQuantity(cursor.getInt(2));
                    bill.setPrice(cursor.getDouble(3));
                    bill.setTotal(cursor.getDouble(4));
                    bill.setState(cursor.getInt(5));
                    bill.setImg(cursor.getBlob(6));
                    cursor.moveToNext();
                    listBill.add(bill);

                }

                cursor.close();
                billRecyclerView.notifyDataSetChanged();

            }
        });
        return view;
    }
    private void addRe(){
        billRecyclerView = new BillRecyclerView(listBill,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerBill.setAdapter(billRecyclerView);
        recyclerBill.setLayoutManager(linearLayoutManager);
    }

    private void addControls(View view) {
        recyclerBill = view.findViewById(R.id.rclBill);
        btnRenew= view.findViewById(R.id.btnRenew);
        listBill = new ArrayList<>();
        getList();

    }

    @Override
    public void onStart() {
        super.onStart();
        if(listBill.size()>0){
            listBill.clear();
            getList();
        }
    }

    private void getList() {
        String query = "select  o.id_Order , pro.name, od.number, od.price,od.price_total,o.id_State,pro.image1\n" +
                "from Order_Detail od,Orders o, Product pro, Customer cus\n" +
                "where od.id_order = o.id_Order and pro.id_product = od.id_Product and o.id_Cus = cus.id_Customer and cus.id_Customer =?";
        Cursor cursor = PageMainCus.database.rawQuery(query,new String[]{String.valueOf(PageMainCus.customer.getId_cus())});
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            Bill bill = new Bill();
            bill.setId(cursor.getInt(0));
            bill.setName(cursor.getString(1));
            bill.setQuantity(cursor.getInt(2));
            bill.setPrice(cursor.getDouble(3));
            bill.setTotal(cursor.getDouble(4));
            bill.setState(cursor.getInt(5));
            bill.setImg(cursor.getBlob(6));
            cursor.moveToNext();
            listBill.add(bill);
            size = listBill.size();
        }
        Log.e("S","tong bill"+listBill.size()+"");
        cursor.close();
    }
}