package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.banquanao.R;
import com.example.banquanao.database.Category;
import com.example.banquanao.database.Product;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String TAG = ProductDetailActivity.class.getName();
    TextView txtidP;
    EditText edtNamePD,edtPriceD,edtQuantityD;
    ImageView ivPro;
    Product product;
    Button btnDe,btnUp,btnCatePD,btnSizePD;
    Toolbar toolbar;

    Category cate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        addControl();
        addEvent();

    }

    private void addEvent() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        btnSizePD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetailActivity.this, ListSizeActivity.class);
                sizeActivityResult.launch(i);
            }
        });
        btnCatePD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetailActivity.this, ListCateActivity.class);
                categoryActivityResult.launch(i);
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct(product);

            }
        });
        btnDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePro(product.getId());

            }

            private void deletePro(int idProduct) {
                if(checkProOrder(idProduct)){
                    // hiển thị là san phẩm đã đc đặt xoa cái j
                    Toast.makeText(getApplicationContext(),"Sản phẩm đã được đặt không xóa được",Toast.LENGTH_SHORT).show();
                    return;
                }
                // xoa
                int kq = PageMainLoginActivity.database.delete("Product","id_Product =?",new String[]{ String.valueOf(idProduct)});
                if(kq>0){
                    Toast.makeText(getApplicationContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Xóa không thành công",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name",edtNamePD.getText().toString());
        values.put("price",edtPriceD.getText().toString());
        values.put("id_Size",btnSizePD.getText().toString().trim());
        values.put("quantity",edtQuantityD.getText().toString().trim());

        values.put("id_Category",cate.getId());
        int kq = PageMainLoginActivity.database.update("Product", values,"id_product =?", new String[]{String.valueOf(product.getId())});
        if (kq > 0) {
            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkProOrder(int idProduct) {
        String query = "select id_Product from Order_Detail;";
        Cursor c = PageMainLoginActivity.database.rawQuery(query , null);
        while(c.moveToNext()){

            if(c.getInt(0)==idProduct){
                return true;
            }
        }
        return false;
    }
    private void addControl() {
        product = new Product();
        txtidP = findViewById(R.id.txtIdProDetail);
        edtNamePD = findViewById(R.id.edtNameProDetail);
        btnSizePD = findViewById(R.id.btnSizePDetail);
        edtPriceD= findViewById(R.id.edtPricePDetail);
        edtQuantityD = findViewById(R.id.edtQuantityPDetail);
        btnCatePD = findViewById(R.id.btnCatePDetail);
        ivPro = findViewById(R.id.ivProDetail);
        toolbar=findViewById(R.id.toolBarProDetail);
        btnDe=findViewById(R.id.btnDePro);
        btnUp=findViewById(R.id.btnUpAcc);
        getData();

    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra("Product_Fragment")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                product = intent.getSerializableExtra("Product_Fragment", Product.class);

                display();
            }
        }
    }
    private void display() {
        cate = new Category();
        cate.setName(getNameCate(product.getId_category()));
        cate.setId(product.getId_category());
        edtNamePD.setText( product.getName());
        btnCatePD.setText(getNameCate(product.getId_category()));
        edtQuantityD.setText( product.getQuantity()+"");
        btnSizePD.setText(product.getSize()+"");
        txtidP.setText("ID : "+product.getId()+"");
        edtPriceD.setText(product.getPrice()+"");

        if(product.getIdResourse()!=null){
            ivPro.setImageBitmap(HomeActivity.TranslateByteToBitmap(product.getIdResourse()));
        }
    }

    public String getNameCate(int idCate) {
        String query = "select name from Caterogy where id_Category =?";
        Log.e("s",product.getId_category()+" idcate ");
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,new String[]{String.valueOf(idCate)});
        cursor.moveToFirst();
        Log.e("s","Ten category: "+ cursor.getString(0));
        return  cursor.getString(0);
    }
    ActivityResultLauncher<Intent> categoryActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        Intent data = result.getData();
                        cate= (Category) data.getSerializableExtra("Category_Item_Extra_Key_Name");
                        btnCatePD.setText(cate.getName());
                    }
                }
            });
    ActivityResultLauncher<Intent> sizeActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    int pro = data.getIntExtra("Size_Item_Extra_Key_Name",0);
                    btnSizePD.setText(pro+"");
                }
            });

}