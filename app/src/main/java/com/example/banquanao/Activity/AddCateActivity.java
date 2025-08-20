package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.banquanao.R;
import com.example.banquanao.database.Category;

import java.util.ArrayList;
import java.util.List;

public class AddCateActivity extends AppCompatActivity {
    List<Category> listCate;
    EditText editTextNameCate;
    Button btnSave;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cate);
        addControl();
        addEvent();

    }

    private void addEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameCate= editTextNameCate.getText().toString().trim();
                if(nameCate.equals("")){
                    Toast.makeText(AddCateActivity.this, "Nhap day du thong tin ", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues values=new ContentValues();
                values.put("name",nameCate);
                values.put("id_Category",getIdMax()+1);
                long kq= PageMainLoginActivity.database.insert("Caterogy",null,values);
                if(kq>0)
                {

                    Toast.makeText(AddCateActivity.this, "Insert success", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddCateActivity.this, "Insert new recoerd fail", Toast.LENGTH_SHORT).show();
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
    public int getIdMax(){
        String query = "select id_Category from Caterogy";
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
        toolbar=findViewById(R.id.materialToolbarAddCate);
        btnSave=findViewById(R.id.btnSave);
        editTextNameCate=findViewById(R.id.edtNameCate);
        listCate = new ArrayList<>();
    }
}