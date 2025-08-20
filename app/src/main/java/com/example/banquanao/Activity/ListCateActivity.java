package com.example.banquanao.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Adapter.CategoryAdapter;
import com.example.banquanao.database.Category;

import java.io.Serializable;
import java.util.ArrayList;

public class ListCateActivity extends AppCompatActivity implements CategoryAdapter.CategoryOnClickListener {


    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;

    public static ArrayList<Category> lstCate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cate);
        addControl();

    }

    private void addControl() {

        recyclerView = findViewById(R.id.RecyclerViewCateList);
        lstCate=new ArrayList<>();
        getData();
        categoryAdapter= new CategoryAdapter(this,lstCate,this);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter.notifyDataSetChanged();

    }

    private void getData() {
        String query ="select * from Caterogy";
        Cursor c=PageMainLoginActivity.database.rawQuery(query,null);
        while (c.moveToNext()){
            int id_Category = c.getInt(0);
            String name = c.getString(1);
            Category category=new Category(id_Category,name);
            lstCate.add(category);

        }
    }


    @Override
    public void didSelectAt(int position) {
        Intent i = new Intent();
        i.putExtra("Category_Item_Extra_Key_Name", (Serializable) lstCate.get(position));
        setResult(RESULT_OK,i);
        finish();
    }
}