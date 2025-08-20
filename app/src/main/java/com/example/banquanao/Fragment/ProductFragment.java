package com.example.banquanao.Fragment;

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
import com.example.banquanao.Activity.AddProActivity;
import com.example.banquanao.Activity.HomeActivity;
import com.example.banquanao.Adapter.ProductAdapter;
import com.example.banquanao.database.Product;

import java.util.ArrayList;


public class ProductFragment extends Fragment {
    RecyclerView recyclerView;
    Button btAdd;
    ProductAdapter adapter;

    private ArrayList<Product> listProduct;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        addControl(view);
        // Inflate the layout for this fragment

        return view;
    }

    private void addControl(View view) {
        btAdd=view.findViewById(R.id.btnAddP);
        recyclerView=view.findViewById(R.id.RecyclerViewP);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        listProduct = new ArrayList<>();
        adapter= new ProductAdapter(this,listProduct);
        recyclerView.setAdapter(adapter);
        getData();
        recyclerView.setLayoutManager(linearLayoutManager);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddProActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(listProduct.size()>0){
            listProduct.clear();
            getData();
        }
    }

    private void getData() {

        String query ="select * from Product";// where idProduct = ?;
        Cursor c= PageMainLoginActivity.database.rawQuery(query,null);
        while (c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            double price = c.getDouble(2);
            int idSize = c.getInt(3);
            int idCate = c.getInt(4);
            int quantity = c.getInt(8);
            byte[] img = c.getBlob(5);
            Product product = new Product(img,name,id,price,idSize,idCate,quantity);
            listProduct.add(product);
        }
        adapter.notifyDataSetChanged();

    }


}