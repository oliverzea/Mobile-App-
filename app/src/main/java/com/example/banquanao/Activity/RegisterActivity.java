package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banquanao.R;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    Button btBack, btLogin, btRegister;
    EditText edPass, edRePass, edName, edPhone, edMail, edAddress;
    ImageView img;
    boolean Visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addControll();
        addEvent();
        
    }
    private void addControll() {
        edName = findViewById(R.id.editTextName_ReCos);
        edPhone = findViewById(R.id.editTextPhone_ReCos);
        edMail = findViewById(R.id.editTextEmail_ReCos);
        edPass = findViewById(R.id.editTextNewPass_ReCos);
        edRePass = findViewById(R.id.editTextRePass_ReCos);
        edAddress = findViewById(R.id.editTextAddress_ReCos);

        clickRegister();
    }
    private void clickRegister() {
        btRegister = findViewById(R.id.buttonRegister_Cos);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVarRegister();
            }
        });
    }
    private void checkVarRegister() {
        String name, email, address, pass, repass;
        int phone = 0;

        name = edName.getText().toString().trim();
        pass = edPass.getText().toString().trim();
        repass = edRePass.getText().toString().trim();
        address = edAddress.getText().toString().trim();
        email = edMail.getText().toString().trim();

        if(name.equals("") && phone==0 && email.equals("") && pass.equals("") && repass.equals("") && address.equals("")){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            return;
        }
        if(name.equals("")){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập tên của bạn",Toast.LENGTH_SHORT).show();
            return;
        }
        if(isPhoneNumberValid(edPhone.getText().toString().trim())==false){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập kiểm tra lại số điện thoại",Toast.LENGTH_SHORT).show();
            return;
        }
        if(edPhone.getText().toString().trim().equals("")==false){
            phone = Integer.parseInt(edPhone.getText().toString().trim());
        }
        if(isPhoneExists(phone)==false){
            Toast.makeText(getApplicationContext(),"Số điện thoại đã tồn tại",Toast.LENGTH_SHORT).show();
            return;
        }
        if(email.equals("") || isValidEmail(email)==false){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập kiểm tra lại email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(isEmailExists(email) == false){
          Toast.makeText(getApplicationContext(),"Email đã tồn tại",Toast.LENGTH_SHORT).show();
          return;
        }
        if(pass.equals("")){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
            return;
        }
        if(repass.equals("")||isPasswordMatch(pass, repass) == false){
            Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp, vui lòng kiểm tra lại",Toast.LENGTH_SHORT).show();
            return;
        }
        if(address.equals("")){
            Toast.makeText(getApplicationContext(),"Vui lòng nhâp địa chỉ",Toast.LENGTH_SHORT).show();
            return;
        }

        insertCus(name, repass, email, phone, address);
    }
    private void insertCus(String name , String pass, String email, int phone, String address){
        ContentValues values = new ContentValues();
        values.put("id_Customer", getMaxIdCus());
        values.put("name", name);
        values.put("passWork_cus", pass);
        values.put("email", email);
        values.put("number_phone", phone);
        values.put("address", address);
        values.put("idPowerCus", 0);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icon_user);
        values.put("Image_Customer",PageMainCus.TranslateBitmapToByte(bitmap));
        long kq = PageMainLoginActivity.database.insert("Customer",null, values);
        if(kq > 0){
             Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
             Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
             startActivity(i);
             Toast.makeText(getApplicationContext(),"Register success",Toast.LENGTH_SHORT).show();
        }else{
             Toast.makeText(getApplicationContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    private int getMaxIdCus(){
        int max =0;
        String query = "select id_Customer from Customer";
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
        while (cursor.moveToNext()) {
            if(max <cursor.getInt(0)){
                max = cursor.getInt(0);
            }
        }
        return max + 1;
    }
    public static boolean isEmailExists(String emailEnter){
        String query = "select email from Customer";
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
        while (cursor.moveToNext()){
            String email = cursor.getString(0).trim();
            if(emailEnter.equals(email) == true){
                return false;
            }
        }
        return true;
    }
    public static boolean isPhoneExists(int phoneEnter){
        String query = "select number_phone from Customer";
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
        while (cursor.moveToNext()){
            int phone = cursor.getInt(0);
            if(phoneEnter == phone){
                return false;
            }
        }
        return true;
    }
    public static boolean isValidEmail(String email) {
        // Regular expression for basic email format
        String regex = "[^@]+@[^@]+\\.[^@]+";
        // Check if email matches the format
        if (!Pattern.matches(regex, email)) {
            return false;
        }
        // Check if email ends with "@gmail.com"
        return email.endsWith("@gmail.com");
    }
    public static boolean isPhoneNumberValid(String phone) {
        // Chuyển đổi số điện thoại sang chuỗi
        String strPhone = String.valueOf(phone);
        // Kiểm tra độ dài chuỗi
        if (strPhone.length() < 10 || strPhone.length() > 10) {
            return false;
        }
        // Kiểm tra ký tự đầu tiên
        if (!Character.isDigit(strPhone.charAt(0))) {
            return false;
        }
        // Kiểm tra các ký tự còn lại
        for (int i = 1; i < strPhone.length(); i++) {
            if (!Character.isDigit(strPhone.charAt(i)) && strPhone.charAt(i) != '-') {
                return false;
            }
        }
        // Số điện thoại hợp lệ
        return true;
    }
    public static boolean isPasswordMatch(String pass, String repass) {
        // So sánh hai mật khẩu
        return pass.equals(repass);
    }


    private void addEvent() {
        eventBack();
        eventShowPass();
        clickLogin();
    }
    private void eventBack() {
        btBack = findViewById(R.id.buttonBack_ReCos);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, PageMainLoginActivity.class);
                startActivity(i);
            }
        });
    }
    private void eventShowPass() {
        edPass = findViewById(R.id.editTextNewPass_ReCos);
        edPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= edPass.getRight()-edPass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edPass.getSelectionEnd();
                        if (Visible){
                            // set drawable image
                            edPass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock,0,R.drawable.visibility_off,0);
                            // for hide password
                            edPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            Visible = false;
                        }else {
                            // set drawable image
                            edPass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock,0,R.drawable.visibility,0);
                            // for hide password
                            edPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            Visible = true;
                        }
                        edPass.setSelection(selection);
                        return  true;
                    }
                }
                return false;
            }
        });

        edRePass = findViewById(R.id.editTextRePass_ReCos);
        edRePass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= edRePass.getRight()-edRePass.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edRePass.getSelectionEnd();
                        if (Visible){
                            // set drawable image
                            edRePass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock,0,R.drawable.visibility_off,0);
                            // for hide password
                            edRePass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            Visible = false;
                        }else {
                            // set drawable image
                            edRePass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.lock,0,R.drawable.visibility,0);
                            // for hide password
                            edRePass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            Visible = true;
                        }
                        edRePass.setSelection(selection);
                        return  true;
                    }
                }
                return false;
            }
        });
    }
    private void clickLogin() {
        btLogin = findViewById(R.id.buttonLogin_ReCos);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

}