package com.example.banquanao.DetailMain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banquanao.database.Cart;
import com.example.banquanao.Fragment.HomeFragment;
import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;

import com.example.banquanao.database.Product;
import com.example.banquanao.database.Util;

public class DetailProduct extends AppCompatActivity  {
    TextView name, price,sizeProduct;
    Button btnAddCart,SUm,Sub;
    ImageView imgPro;
    EditText quantity;
    Product product;
    int Quantity_data=0;
    Context context=this;
    Cart cart ;
    public static int quantityOfDetail=0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        setSupportActionBar(findViewById(R.id.toolbarDetail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addControl();
        GetData();
        handleButton();


    }

    private void addControl() {
        imgPro = findViewById(R.id.imgProductDetail);
        name = findViewById(R.id.nameProductDetail);
        price = findViewById(R.id.priceProductDetail);
        btnAddCart = findViewById(R.id.btnAddCart);
        Sub= findViewById(R.id.btnSub);
        SUm= findViewById(R.id.btnSum);
        quantity=findViewById(R.id.editQuantity);
        sizeProduct=findViewById(R.id.sizeProductDetail);
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("Product");
        cart = new Cart();
    }

    protected void GetData(){
        name.setText(product.getName());
        price.setText(""+ product.getPrice());
        Bitmap bitmap = PageMainCus.TranslateByteToBitmap(product.getIdResourse());
        imgPro.setImageBitmap(bitmap);
        String size = product.getWidth()+"cm "+product.getHeight()+"cm "+product.getWeight()+"kg "+ product.getSex()+" "+product.getType();
        sizeProduct.setText("Size: " +size);
        Quantity_data = HomeFragment.quantityOfProduct[product.getId()];
    }

    @Override
    protected void onStart() {
        super.onStart();
        Quantity_data = HomeFragment.quantityOfProduct[product.getId()];

    }

    private void handleButton(){
       Sub.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              int q = Integer.parseInt(quantity.getText().toString().trim());
              if(q<1){
                  Toast.makeText(getApplicationContext(),"Khong hop le!",Toast.LENGTH_SHORT).show();
              }else{
                  int q_new = q-1;
                  quantity.setText(q_new +" ");

              }
           }
       });

        SUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = Integer.parseInt(quantity.getText().toString().trim());
                if(PageMainCus.utils.isEmpty()){
                    q=q+1;
                   // Log.e("e","q= "+q);
                    if(q>Quantity_data){
                        Toast.makeText(getApplicationContext(),"Kho hang ko du",Toast.LENGTH_SHORT).show();
                    }else{
                        quantity.setText(q+" ");
                    }
                }else{
                    q++;
                  //  Log.e("e","q="+q +"\n");
                    if(checkQuantity(q,product.getId())){
                        if(q>Quantity_data){
                            Toast.makeText(getApplicationContext(),"Kho hang ko du",Toast.LENGTH_SHORT).show();
                        }else{
                            quantity.setText(q+" ");
                        }


                    }else{
                        Toast.makeText(getApplicationContext(),"Kho hang ko du",Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAdd(product.getId())||checkQuantityInProdcut(product.getId())){
                    PageMainCus.MyToast(context,"San pham da het ");
                    finish();
                }else {
                    cart.setId(product.getId());
                    cart.setName(product.getName());
                    cart.setImg(product.getIdResourse());
                    cart.setPrice(product.getPrice());
                    int getQuantity = Integer.parseInt(quantity.getText().toString().trim());
                    cart.setQuantity_cart(getQuantity);
                    cart.setTotal(cart.getQuantity_cart()*cart.getPrice());
                    addUltis(cart.getId(),cart.getQuantity_cart());

                    Intent intent =new Intent();
                    intent.putExtra("cart",cart);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
    public void addUltis(int idCart,int quantity){
          if(PageMainCus.utils.size()>0){
              boolean flag = false;
              for(int i = 0; i< PageMainCus.utils.size(); i++){
                  if(PageMainCus.utils.get(i).getId_pro()==idCart){
                      PageMainCus.utils.get(i).setQuantity(quantity+ PageMainCus.utils.get(i).getQuantity());
                      flag = true;
                  }
              }

              if(flag == false){
                  PageMainCus.utils.add(new Util(idCart,quantity));
              }
          }else{
              PageMainCus.utils.add(new Util(idCart,quantity));
          }

    }

    public boolean checkQuantity(int quantityOld,int idCart){

        for (int i = 0; i< PageMainCus.utils.size(); i++){
            if(PageMainCus.utils.get(i).getId_pro()==idCart){
                if(quantityOld+ PageMainCus.utils.get(i).getQuantity()>Quantity_data){
                    int sum =quantityOld+ PageMainCus.utils.get(i).getQuantity();
                  //  Log.e("Tong","Tong: "+sum+" ");
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkAdd(int idcart){
        if(PageMainCus.utils.size()>0){
            for(int i = 0; i< PageMainCus.utils.size(); i++){
                if(idcart== PageMainCus.utils.get(i).getId_pro()){
                    if(PageMainCus.utils.get(i).getQuantity()>=Quantity_data){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean checkQuantityInProdcut(int idCart){
        for(int i=0;i< HomeFragment.quantityOfProduct.length;i++){
            if(HomeFragment.quantityOfProduct[idCart]<=0){
                return true;
            }
        }
        return false;
    }
}