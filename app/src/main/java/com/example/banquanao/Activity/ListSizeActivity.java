package com.example.banquanao.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Adapter.SizeAdapter;
import com.example.banquanao.database.Size;

import java.util.ArrayList;

public class ListSizeActivity extends AppCompatActivity implements SizeAdapter.SizeOnClickListener{

    RecyclerView recyclerViewSize;
    SizeAdapter sizeAdapter;
    private ArrayList<Size> lstSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_size);
        addControl();
    }

    private void addControl() {
        recyclerViewSize = findViewById(R.id.RecyclerViewSizeList);
        lstSize=new ArrayList<>();
        getData();
        sizeAdapter= new SizeAdapter(this,lstSize,this);
        recyclerViewSize.setAdapter(sizeAdapter);
        recyclerViewSize.setLayoutManager(new LinearLayoutManager(this));
        sizeAdapter.notifyDataSetChanged();
    }

    private void getData() {
        String query ="select * from Size";
        Cursor c=PageMainLoginActivity.database.rawQuery(query,null);
        while (c.moveToNext()){
            int id_Size = c.getInt(0);
            float height  = c.getInt(2);
            float weight  = c.getInt(3);
            String sex = c.getString(4);
            String type = c.getString(5);
            Size size=new Size(id_Size,height,weight,sex,type);
            lstSize.add(size);

        }
    }

    @Override
    public void didSelectAtSize(int position) {
        Intent i = new Intent();
        Log.e("S",lstSize.get(position).getIdSize()+" List size Activity");
        i.putExtra("Size_Item_Extra_Key_Name",lstSize.get(position).getIdSize());
        setResult(RESULT_OK,i);
        finish();
    }
}