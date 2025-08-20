package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.banquanao.R;
import com.example.banquanao.database.Staff;

import java.util.regex.Pattern;

public class AccountActivity extends AppCompatActivity {

    ImageView ivStaffAcc;
    TextView txtIdAcc;
    EditText edtName,edtPhone,edtEmail;
    Button btnUpAcc;
    Toolbar toolbar;
    Staff staff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        addControl();
        getData();
        display();
        addEvent();
    }

    private void addEvent() {
        btnUpAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAcc(staff);
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

    private void updateAcc(Staff staff) {

        String name = edtName.getText().toString().trim();
        int numberphone = Integer.parseInt(edtPhone.getText().toString().trim());
        String email = edtEmail.getText().toString().trim();

        if(name.equals("")|| numberphone<=0 ||email.equals("")){
            Toast.makeText(getApplicationContext(), "Nhập đẩy đủ thông tin !", Toast.LENGTH_SHORT).show();
            return;
        }


        if(isValidEmail(email)==false){
            Toast.makeText(getApplicationContext(), "Nhập Email đúng định dạng !", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("number_phone",numberphone);
        values.put("email",email);
        int kq = PageMainLoginActivity.database.update("Staff", values,"idManager =?", new String[]{String.valueOf(staff.getIdManager())});
        if (kq > 0) {
            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            HomeActivity.staff.setName(name);
            HomeActivity.staff.setNumber_phone(numberphone);
            HomeActivity.staff.setEmail(email);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void display() {
        edtName.setText(staff.getName());
        txtIdAcc.setText(staff.getId_Staff()+"");
        edtPhone.setText(String.valueOf(staff.getNumber_phone()));
        edtEmail.setText(staff.getEmail());
        byte[] bytes = staff.getImageStaff();
        if (bytes != null) {
            // Convert byte array to a Bitmap
            Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ivStaffAcc.setImageBitmap(image);
        }
    }

    private void getData() {
        Intent intent = getIntent();
        if(intent.hasExtra("AccountStaffMana")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    staff = intent.getSerializableExtra("AccountStaffMana", Staff.class);
            }
        }
    }

    private void addControl() {
        txtIdAcc=findViewById(R.id.txtIdAcc);
        edtName=findViewById(R.id.edtNameAcc);
        edtEmail=findViewById(R.id.edtEmailAcc);
        edtPhone=findViewById(R.id.edtPhoneAcc);
        btnUpAcc=findViewById(R.id.btnUpAcc);
        toolbar=findViewById(R.id.materialToolbarAcc);
        ivStaffAcc=findViewById(R.id.imageViewAvatar);
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,6}$";
        return Pattern.matches(regex, email);
    }
}