package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banquanao.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.banquanao.database.Size;
import java.util.ArrayList;
import java.util.List;

public class AddSizeActivity extends AppCompatActivity {

    List<Size> listSize;
    Button btnSaveS;
    EditText edtWeightA,edtHeightA,edtTypeA,edtSexA;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_size);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnSaveS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = edtHeightA.getText().toString().trim();
                String weight = edtWeightA.getText().toString().trim();
                String sex = edtSexA.getText().toString().trim();
                String type = edtTypeA.getText().toString().trim();

                if(height.equals("")||weight.equals("")||sex.equals("")||type.equals("")){
                    Toast.makeText(AddSizeActivity.this, "Nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues values=new ContentValues();
                values.put("id_Size",getIdMax()+1);
                values.put("heigh",edtHeightA.getText().toString()+"");
                values.put("weight",edtWeightA.getText().toString()+"");
                values.put("sex",edtSexA.getText().toString());
                values.put("type",edtTypeA.getText().toString());
                long kq= PageMainLoginActivity.database.insert("Size",null,values);
                if(kq>0)
                {

                    Toast.makeText(AddSizeActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddSizeActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                finish();
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

    private int getIdMax() {
        String query = "select id_Size from Size";
        Cursor c = PageMainLoginActivity.database.rawQuery(query,null);
        c.moveToFirst();
        int max = c.getInt(0);
        while (c.moveToNext()){
            if (max < c.getInt(0)) {
                max=c.getInt(0);
            }
        }
        return max;
    }

    private void addControl() {
        btnSaveS=findViewById(R.id.btnSaveSize);
        edtHeightA=findViewById(R.id.edtHeightAdd);
        edtWeightA=findViewById(R.id.edtWeightAdd);
        edtSexA=findViewById(R.id.edtSexAdd);
        edtTypeA=findViewById(R.id.edtTypeAdd);
        toolbar=findViewById(R.id.materialToolbarAddSize);
        listSize=new ArrayList<>();
    }
}