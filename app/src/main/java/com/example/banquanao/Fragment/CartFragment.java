package com.example.banquanao.Fragment;

import static android.app.Activity.RESULT_OK;
import static com.example.banquanao.Activity.PageMainCus.check_click;
import static com.example.banquanao.Activity.PageMainCus.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.banquanao.database.Cart;
import com.example.banquanao.Adapter.CartAdapter;
import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.Activity.OrderPageActivity;
import com.example.banquanao.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    public static List<Cart> mlistCart;
   public static CartAdapter cartAdapter ;
    Cart cart;

    public static ListView listView ;
    public static double total_price=0;
    public static TextView total;
    public static List<Cart> sendCart,backupCart;
    Button btnOrder;
    public static String nameShared="ListCart";
    SharedPreferences sharedPreferences;
    public static final int REQUEST_CODE =21;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("test","Create view");
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        AddControlsCart(view);
        if(PageMainCus.backup==null){
            PageMainCus.backup = new ArrayList<>();
            Log.e("Test","Mang null");
        }else{
            mlistCart= PageMainCus.backup;
            cartAdapter = new CartAdapter(mlistCart,getContext());
            listView.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
            total.setText(0+"");
            total_price=0;

        }
        Log.e("Test","Mang null");
        handleOrder();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Test","Onstart");
        if(sendCart.isEmpty()){
            Log.e("Test","Dang rong neeeee");
        }else {
            Log.e("test",sendCart.size()+" ");
        }
    }


    private void AddControlsCart(View view) {
        mlistCart = new ArrayList<>();
        listView = view.findViewById(R.id.listCart);
        cart= new Cart();
        total = view.findViewById(R.id.totalPrice);
        btnOrder = view.findViewById(R.id.btnDatHang);
        context=view.getContext();
        sendCart = new ArrayList<>();
        sharedPreferences = context.getSharedPreferences(nameShared,Context.MODE_PRIVATE);
        backupCart= new ArrayList<>();
        if(check_click==null){
            check_click= new boolean[100];
        }
        init();
    }

    private void init() {
        for(int i=0;i<check_click.length;i++){
            check_click[i]=false;
        }
    }


    public void getData(Cart cart) {

        if(mlistCart.size()>0){
            boolean flag=false;
            for (int i=0;i<mlistCart.size();i++){
                if(mlistCart.get(i).getId()==cart.getId()){
                    sendCart= new ArrayList<>();
                    init();
                    check_click[cart.getId()]=false;
                    double total_old=mlistCart.get(i).getTotal();
                    mlistCart.get(i).setQuantity_cart(mlistCart.get(i).getQuantity_cart()+cart.getQuantity_cart());
                    mlistCart.get(i).setTotal(mlistCart.get(i).getQuantity_cart()*cart.getPrice());
                    total_price=total_price-total_old;
                    total_price+=mlistCart.get(i).getTotal();
                    PageMainCus.total_backup=total_price;
                    total_price=0;
                    total.setText(total_price+"");
                    flag =true;
                }
            }
            if(flag==false){
                mlistCart.add(cart);
                sendCart= new ArrayList<>();
                init();
                check_click[cart.getId()]=false;
                total_price += cart.getTotal();
                PageMainCus.total_backup=total_price;
                total_price=0;
                total.setText(total_price+"");
            }
        }else{
            mlistCart.add(cart);
            init();
            sendCart= new ArrayList<>();
            total_price += cart.getTotal();
            Toast.makeText(getContext(),cart.getName(),Toast.LENGTH_SHORT).show();
            PageMainCus.total_backup=total_price;
            total_price=0;
            total.setText(total_price+"");
            check_click[cart.getId()]=false;
        }
        //Log.e("s",sendCart.get(0).getName()+"");
        PageMainCus.backup = mlistCart;
        saveData();
        backup();
    }




    private void handleOrder() {

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sendCart.isEmpty()){
                    Intent intent = new Intent(context, OrderPageActivity.class);
                    intent.putParcelableArrayListExtra("cartList", (ArrayList<? extends Parcelable>) sendCart);
                    intent.putExtra("user",customer);
                    startActivityForResult(intent,REQUEST_CODE);
                }else{
                    PageMainCus.MyToast(getContext(),"Gio hang trong");
                }

            }
        });
    }
    private void backup(){
        SharedPreferences getdata= context.getSharedPreferences("ListCart",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = getdata.getString("listcart",null);
        if(json==null){
            Log.e("Check","Ko co");
        }else {
            Type type = new TypeToken<List<Cart>>(){}.getType();
            mlistCart =gson.fromJson(json,type);;
            cartAdapter = new CartAdapter(mlistCart,getContext());
            listView.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
        }

    }



    private void saveData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(mlistCart);
        editor.putString("listcart",json);
        editor.apply();
    }

    private void deleteData(){
        sharedPreferences.edit().clear().apply();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("líst", (ArrayList<? extends Parcelable>) mlistCart);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK){
            Log.e("s","Day la  on activity result");
            PageMainCus.MyToast(getContext(),"Day la  on activity result");
           // Log.e("s","send cart sau khi dat hang: "+sendCart.size()+"");
            mlistCart.removeAll(sendCart);
            deleteData();
            PageMainCus.backup=null;
            PageMainCus.total_backup=0;
            cartAdapter = new CartAdapter(mlistCart,getContext());
            total.setText(0+"");
            total_price=0;
            listView.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
            PageMainCus.utils= new ArrayList<>();
            sendCart=new ArrayList<>();

        }
    }
}