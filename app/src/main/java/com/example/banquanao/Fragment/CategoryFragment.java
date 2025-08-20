package com.example.banquanao.Fragment;

import android.content.Context;
import android.content.Intent;
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

import com.example.banquanao.Activity.PageMainLoginActivity;
import com.example.banquanao.R;
import com.example.banquanao.Activity.AddCateActivity;
import com.example.banquanao.Activity.CategoryDetailActivity;
import com.example.banquanao.Activity.HomeActivity;
import com.example.banquanao.Adapter.CategoryAdapter;
import com.example.banquanao.database.Category;

import java.util.ArrayList;

public class CategoryFragment extends Fragment implements  CategoryAdapter.CategoryOnClickListener{

    RecyclerView recyclerView;
    Button btAdd;
    CategoryAdapter adapter;
    Context context;
    private ArrayList<Category> listCate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        addControl(view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(listCate.size()>0){
            listCate.clear();
            String query ="select * from Caterogy";// where idProduct = ?;
            Cursor c= PageMainLoginActivity.database.rawQuery(query,null);
            while (c.moveToNext()){
                int id = c.getInt(0);
                String name = c.getString(1);
                Category category = new Category(id,name);
                listCate.add(category);

            }
            c.close();
            adapter.notifyDataSetChanged();
        }
    }

    private void addControl(View view) {
        btAdd=view.findViewById(R.id.btnAddCate);
        context=view.getContext();
        recyclerView=view.findViewById(R.id.RecyclerViewCate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listCate = new ArrayList<>();
        getData();
        adapter= new CategoryAdapter(getContext(),listCate,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddCateActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        String query ="select * from Caterogy";// where idProduct = ?;
        Cursor c= PageMainLoginActivity.database.rawQuery(query,null);
        c.moveToFirst();

        while (c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            Category category = new Category(id,name);
            listCate.add(category);

        }
        if (listCate.size() > 0) {
            Log.e("h",listCate.get(0).getName());

        }else{
            Log.e("h","mang null");
        }

    }

    @Override
    public void didSelectAt(int position) {
           Intent intent = new Intent(context, CategoryDetailActivity.class);
            intent.putExtra("CaterogyDetail",listCate.get(position));
            context.startActivity(intent);
    }
}