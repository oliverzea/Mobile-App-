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
import com.example.banquanao.database.Size;

public class SizeDetailActivity extends AppCompatActivity {
    TextView txtIdS;
    EditText edtWeight, edtHeight, edtSex, edtType;
    Size size;
    Button btnUpS, btnDeS;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_detail_activity);
        addControl();
        addEvent();
    }

    private void display() {
        txtIdS.setText(size.getIdSize()+"");
        edtWeight.setText(size.getWeight() + "");
        edtHeight.setText(size.getHeight() + "");
        edtSex.setText(size.getSex());
        edtType.setText(size.getType());
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent.hasExtra("SizeDetail")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                size = intent.getSerializableExtra("SizeDetail", Size.class);
                display();
            }
        }
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
        btnUpS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSize(size);
            }
        });
        btnDeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSize(size.getIdSize());
            }
        });

    }

    private void deleteSize(int idSize) {
        if (checkSizePro(idSize)) {
            // hiển thị là san phẩm đã đc đặt xoa cái j
            Toast.makeText(getApplicationContext(), "Size đã có sản phẩm không xóa được", Toast.LENGTH_SHORT).show();
            return;
        }
        // xoa
        int kq = PageMainLoginActivity.database.delete("Size", "id_Size =?", new String[]{String.valueOf(idSize)});
        if (kq > 0) {
            Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkSizePro(int idSize) {
        String query = "select id_Size from Product;";
        Cursor c = PageMainLoginActivity.database.rawQuery(query , null);
        while(c.moveToNext()){

            if(c.getInt(0)==idSize){
                return true;
            }
        }
        return false;
    }



    private void updateSize(Size size) {
        ContentValues values = new ContentValues();
        values.put("weight",edtWeight.getText().toString());
        values.put("heigh",edtHeight.getText().toString());
        values.put("sex",edtSex.getText().toString());
        values.put("type",edtType.getText().toString());
        int kq = PageMainLoginActivity.database.update("Size", values,"id_Size =?", new String[]{String.valueOf(size.getIdSize())});
        if (kq > 0) {
            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControl() {
        txtIdS=findViewById(R.id.txtIdSizeD);
        edtWeight=findViewById(R.id.edtWeightD);
        edtHeight=findViewById(R.id.edtHeightD);
        edtSex=findViewById(R.id.edtSexD);
        edtType=findViewById(R.id.edtTypeD);
        toolbar=findViewById(R.id.toolBarSizeDetail);
        btnDeS=findViewById(R.id.btnDeSize);
        btnUpS=findViewById(R.id.btnUpSize);
        getData();
    }
}