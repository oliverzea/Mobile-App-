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
import com.example.banquanao.Adapter.AdapterDeliveredDetail;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Product;

import java.util.ArrayList;
import java.util.List;

public class DeliveryDetailActivity extends AppCompatActivity {

    Customer customer;
    Button btnDeliDetail;
    TextView txtNameCusDeliDetail,txtNumberCusDeliDetail,txtEmailCusDeliDetail;
    RecyclerView recyclerDeliDetail;
    List<Product> mlistProDeli;
    AdapterDeliveredDetail adapterDeliveredDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);

        addControl();
        getDataCusDeli();
        disPlayCusDeli();
        addEvent();
    }

    private void addEvent() {
        btnDeliDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("id_State",4);
                values.put("id_Staff", PageStaffActivity.staff.getId_Staff());

                int kq= PageStaffActivity.database.update("Orders",values,"id_Order=?",new String[]{String.valueOf(getIdOrderDeli())});
                if(kq>0){
                    Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"Không thành công",Toast.LENGTH_SHORT).show();
                }
                finish();;
            }
        });
    }

    private void disPlayCusDeli() {
        txtNameCusDeliDetail.setText(customer.getName_Cus());
        txtNumberCusDeliDetail.setText(customer.getNumberPhone()+"");
        txtEmailCusDeliDetail.setText(customer.getEmail());
    }

    private void getDataCusDeli() {
        Intent intent = getIntent();
        if(intent.hasExtra("Customer")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                customer = intent.getSerializableExtra("Customer",Customer.class);
            }
        }
    }

    private void addControl() {
        setSupportActionBar(findViewById(R.id.toolbarDelidetail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNameCusDeliDetail = findViewById(R.id.txtNameCusDeliDetail);
        txtNumberCusDeliDetail = findViewById(R.id.txtNumberCusDeliDetail);
        txtEmailCusDeliDetail = findViewById(R.id.txtEmailCusDeliDetail);
        mlistProDeli = new ArrayList<>();
        getDataProDeli();
        recyclerDeliDetail = findViewById(R.id.recyclerDeliDetail);
        adapterDeliveredDetail = new AdapterDeliveredDetail(mlistProDeli,this);
        recyclerDeliDetail.setAdapter(adapterDeliveredDetail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerDeliDetail.setLayoutManager(linearLayoutManager);
        btnDeliDetail = findViewById(R.id.btnDeliDetail);
        customer = new Customer();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    private void getDataProDeli(){
        String query ="select * from Order_Detail d , Product p where p.id_product= d.id_Product and d.id_order = ?";
        Cursor c = PageStaffActivity.database.rawQuery(query,new String[]{String.valueOf(getIdOrderDeli())});

        while (c.moveToNext()) {
            byte[] hinhsp = c.getBlob(12);
            String tensp = c.getString(8);
            double dongia = c.getDouble(9);
            int soluong = c.getInt(4);
            Product product = new Product(hinhsp, tensp, dongia, soluong);
            mlistProDeli.add(product);
        }

        c.close();
    }
    private int getIdOrderDeli(){
        Intent intent = getIntent();
        int id=0;
        if(intent.hasExtra("idOrder")){
            id = intent.getIntExtra("idOrder",0);
        }
        return id;
    }
}