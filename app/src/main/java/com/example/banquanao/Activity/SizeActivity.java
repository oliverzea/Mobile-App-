package com.example.banquanao.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Adapter.SizeAdapter;
import com.example.banquanao.database.Size;
import java.util.ArrayList;

public class SizeActivity extends AppCompatActivity implements SizeAdapter.SizeOnClickListener {


    Context context;
    Button btnAddSize;
    SizeAdapter sizeAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
    private ArrayList<Size> listSize;

    @Override
    protected void onStart() {
        super.onStart();
        if (listSize.size()>0){
            listSize.clear();
            getData();
            sizeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnAddSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddSizeActivity.class);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }


    private void addControl() {
        btnAddSize=findViewById(R.id.btnAddSize);
        toolbar=findViewById(R.id.materialToolbarSize);
        recyclerView=findViewById(R.id.RecyclerViewSize);
        context = recyclerView.getContext();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        listSize = new ArrayList<>();
        getData();
        sizeAdapter= new SizeAdapter(context,listSize,this);
        recyclerView.setAdapter(sizeAdapter);
        sizeAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getData() {
        String query ="select * from Size";
        Cursor c= PageMainLoginActivity.database.rawQuery(query,null);
        c.moveToFirst();
        while (c.moveToNext()){
            int id = c.getInt(0);
            double height = c.getDouble(2);
            double weight = c.getDouble(3);
            String sex=c.getString(4);
            String type=c.getString(5);
            Size size = new Size(id,height,weight,sex,type);
            listSize.add(size);

        }
        if (listSize.size() > 0) {
            Log.e("h",listSize.get(0).getSex());

        }else{
            Log.e("h","mang null");
        }
    }


    @Override
    public void didSelectAtSize(int position) {
        Intent intent = new Intent(this, SizeDetailActivity.class);
        intent.putExtra("SizeDetail",listSize.get(position));
        context.startActivity(intent);
    }
}