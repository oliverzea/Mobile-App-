package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Adapter.AdapterOrderDetail;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    Customer customer;
    Button btnOrderDetail;
    TextView nameCus, emailCus,sdtCus;
    RecyclerView recyclerOrderDetail;
    List<Product> mlisPro;
    AdapterOrderDetail adapterOrderDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        addControl();
        getDataCus();
        disPlayCus();
        addEvent();


    }

    private void disPlayCus() {
        nameCus.setText(customer.getName_Cus());
        sdtCus.setText(customer.getNumberPhone()+"");
        emailCus.setText(customer.getEmail());
    }

    private void getDataCus() {
            Intent intent = getIntent();
            if(intent.hasExtra("Customer")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    customer = intent.getSerializableExtra("Customer",Customer.class);
                }
            }
    }

    private void addEvent() {
        btnOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id_State",3);
                values.put("id_Staff", PageStaffActivity.staff.getId_Staff());

                int kq= PageStaffActivity.database.update("Orders",values,"id_Order=?",
                        new String[]{String.valueOf(getIdOrder())});
                if(kq>0){
                    Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"Không thành công",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    private void addControl() {
        setSupportActionBar(findViewById(R.id.idToolbarDetail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameCus= findViewById(R.id.txtNameCusDetail);
        sdtCus= findViewById(R.id.txtSdtDetail);
        emailCus= findViewById(R.id.txtNameEmailDetail);
        mlisPro= new ArrayList<>();
        getDataPro();
        recyclerOrderDetail = findViewById(R.id.recyclerOrderDetail);
        adapterOrderDetail = new AdapterOrderDetail(mlisPro,this);
        recyclerOrderDetail.setAdapter(adapterOrderDetail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerOrderDetail.setLayoutManager(linearLayoutManager);
        btnOrderDetail = findViewById(R.id.btnOrderDetail);
        customer = new Customer();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    private void getDataPro(){
        String query ="select * from Order_Detail d , Product p where p.id_product= d.id_Product and d.id_order = ?";
        Cursor c = PageStaffActivity.database.rawQuery(query,new String[]{String.valueOf(getIdOrder())});

        while (c.moveToNext()) {
            byte[] hinhsp = c.getBlob(12);
            String tensp = c.getString(8);
            double dongia = c.getDouble(9);
            int soluong = c.getInt(4);
            Product product = new Product(hinhsp, tensp, dongia, soluong);
            mlisPro.add(product);
        }

        c.close();
    }
    private int getIdOrder(){
        Intent intent = getIntent();
        int id=0;
        if(intent.hasExtra("idOrder")){
            id = intent.getIntExtra("idOrder",0);
        }
        return id;
    }
}