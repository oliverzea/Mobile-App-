package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.banquanao.R;
import com.example.banquanao.database.Category;

public class CategoryDetailActivity extends AppCompatActivity {

    EditText edtNameCate;
    TextView txtidCateD;
    Button btnDeCate,btnUpCate;
    Category category;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        addControl();
        getDataFromCategory();
        display();
        addEvent();
    }

    private void getDataFromCategory() {
        Intent intent = getIntent();
        if(intent.hasExtra("CaterogyDetail")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                category = intent.getSerializableExtra("CaterogyDetail",Category.class);
            }
        }
    }

    private void addEvent() {
        btnDeCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCate(category.getId());
            }
        });

        btnUpCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtNameCate.getText().toString().trim();
                updateCate(name);
            }
        });
    }

    private void updateCate(String nameCate) {
        ContentValues values = new ContentValues();
        values.put("name",nameCate);

        int kq = PageMainLoginActivity.database.update("Caterogy", values,"id_Category =?", new String[]{String.valueOf(category.getId())});
        if (kq > 0) {
            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCate(int idCategory) {
        if (checkCateProduct(idCategory)) {
            // hiển thị là san phẩm đã đc đặt xoa cái j
            Toast.makeText(getApplicationContext(), "Loại hàng đã có sản phẩm không xóa được", Toast.LENGTH_SHORT).show();
            return;
        }
        // xoa
        int kq = PageMainLoginActivity.database.delete("Caterogy", "id_Category =?", new String[]{String.valueOf(idCategory)});
        if (kq > 0) {
            Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean checkCateProduct(int idCategory) {
        String query = "select id_Category from Product;";
        Cursor c = PageMainLoginActivity.database.rawQuery(query , null);
        while(c.moveToNext()){

            if(c.getInt(0)==idCategory){
                return true;
            }
        }
        return false;
    }
    private void addControl() {
        edtNameCate=findViewById(R.id.edtNameCateDetail);
        txtidCateD=findViewById(R.id.txtIdCateD);
        btnDeCate=findViewById(R.id.btnDeCate);
        btnUpCate=findViewById(R.id.btnUpCate);
        toolbar=findViewById(R.id.toolBarCateDetail);
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
    private void display() {
        edtNameCate.setText(category.getName());
        txtidCateD.setText(category.getId()+"");
    }


}