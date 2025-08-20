package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Adapter.State1DetailAdapter;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Product;

import java.util.ArrayList;
import java.util.List;

public class State1DetailActivity extends AppCompatActivity {

    TextView txtName ,txtState,txtHeader,txtPhone,txtAddress,txtEmail;
    Toolbar toolbar;
    RecyclerView recyclerView;
    State1DetailAdapter adapter;
    Button btnComfirm;
    Customer customer;
    int id_Order ;
    int id_State;
    List<Product> mlisPro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state1_detail);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //phan thay doi thong tin la ContentValues -> nhung thong tin ma thay doi thif lam
                ContentValues values = new ContentValues();
                values.put("id_State",2);
                values.put("id_Manager",1);//chua lay id manager

                //phan nay se la cap nhat du lieu len database
                int kq = PageMainLoginActivity.database.update("Orders",values,"id_Order=?",new String[]{String.valueOf(id_Order)});
                if(kq>0){
                    Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }


    private void addControl() {
        txtName = findViewById(R.id.txtCusNameState1Detail);
        txtState = findViewById(R.id.txtState1Detail);
        txtHeader = findViewById(R.id.txtHeaderS1Detail);
        txtPhone = findViewById(R.id.txtCusPhoneState1Detail);
        txtAddress = findViewById(R.id.txtCusAddressState1Detail);
        txtEmail = findViewById(R.id.txtCusEmailState1Detail);
        recyclerView = findViewById(R.id.rclState1Detail);
        btnComfirm = findViewById(R.id.btnComfirmS1);
        toolbar = findViewById(R.id.toolbarDetailSate1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData();
        mlisPro = new ArrayList<>();
        getProduct();
        adapter = new State1DetailAdapter(mlisPro,getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void getData() {
        Intent intent = getIntent();
        if(intent.hasExtra("id_order")&&intent.hasExtra("Customer")&&intent.hasExtra("id_State")){
            id_Order = intent.getIntExtra("id_order",0);
            id_State=intent.getIntExtra("id_State",0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                customer = intent.getSerializableExtra("Customer",Customer.class);
            }

            showInfor();

        }
    }

    private void showInfor() {
        txtName.setText(customer.getName_Cus());
        txtState.setText(" Chờ xác nhận");
        txtPhone.setText(customer.getNumberPhone()+"");
        txtEmail.setText(customer.getEmail());
        txtHeader.setText("Đơn hàng "+id_Order);
        txtAddress.setText(customer.getAddress());


    }

    private void getProduct(){
        String query = "select * from Order_Detail d , Product p where d.id_Product = p.id_product and d.id_order = ?";
        Cursor c = PageMainLoginActivity.database.rawQuery(query,new String[]{String.valueOf(id_Order)});
        while (c.moveToNext()){
            Log.e("S","Zo ham while ");

            String name = c.getString(8);
            double price = c.getDouble(3);
            int quantity = c.getInt(4);
            int idSize = c.getInt(6);
            double total = price*quantity;
            byte[] bytes  =c.getBlob(12);
            mlisPro.add(new Product(bytes,name,price,idSize,quantity));
        }
        c.close();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}