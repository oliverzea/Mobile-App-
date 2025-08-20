package com.example.banquanao.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Adapter.AdapterSuccessDetail;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Product;

import java.util.ArrayList;
import java.util.List;

public class SuccessActivity extends AppCompatActivity {

    Customer customer;
    TextView txtNameCusSuccess,txtNameEmailSuccess,txtSdtSuccess;
    RecyclerView recyclerSuccess;
    List<Product> mlistSuccess;
    AdapterSuccessDetail adapterSuccessDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        addControl();
        getDataCusSuccess();
        disPlayCusSuccess();
    }

    private void disPlayCusSuccess() {
        txtNameCusSuccess.setText(customer.getName_Cus());
        txtSdtSuccess.setText(customer.getNumberPhone()+"");
        txtNameEmailSuccess.setText(customer.getEmail());
    }

    private void getDataCusSuccess() {
        Intent intent = getIntent();
        if(intent.hasExtra("Customer")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                customer = intent.getSerializableExtra("Customer",Customer.class);
            }
        }
    }

    private void addControl() {
        setSupportActionBar(findViewById(R.id.idToolbarSuccess));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNameCusSuccess = findViewById(R.id.txtNameCusSuccess);
        txtNameEmailSuccess = findViewById(R.id.txtNameEmailSuccess);
        txtSdtSuccess = findViewById(R.id.txtSdtSuccess);
        mlistSuccess = new ArrayList<>();
        getDataSuccess();
        recyclerSuccess =findViewById(R.id.recyclerSuccess);
        adapterSuccessDetail = new AdapterSuccessDetail(mlistSuccess, this);
        recyclerSuccess.setAdapter(adapterSuccessDetail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerSuccess.setLayoutManager(linearLayoutManager);
        customer = new Customer();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    private void getDataSuccess(){
        String query ="select * from Order_Detail d , Product p where p.id_product= d.id_Product and d.id_order = ?";
        Cursor c = PageStaffActivity.database.rawQuery(query,new String[]{String.valueOf(getIdOrderSuccess())});

        while (c.moveToNext()) {
            byte[] hinhsp = c.getBlob(12);
            String tensp = c.getString(8);
            double dongia = c.getDouble(9);
            int soluong = c.getInt(4);
            Product product = new Product(hinhsp, tensp, dongia, soluong);
            mlistSuccess.add(product);
        }

        c.close();
    }
    private int getIdOrderSuccess(){
        Intent intent = getIntent();
        int id=0;
        if(intent.hasExtra("idOrder")){
            id = intent.getIntExtra("idOrder",0);
        }
        return id;
    }
}