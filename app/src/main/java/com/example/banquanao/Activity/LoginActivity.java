package com.example.banquanao.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import com.example.banquanao.R;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Staff;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    Button btBack, btLogin, btRePass, btRegister;
    EditText edAccount, edPass;
    boolean Visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControll();
        addEvent();
    }

    private void addControll() {
        edAccount = findViewById(R.id.editTextAccount_Login);
        edPass = findViewById(R.id.editTextPass_Login);

        clickLogin();
    }
    private void clickLogin() {
        btLogin = findViewById(R.id.buttonLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkVarLogin();
            }
        });
    }
    private void checkVarLogin(){
        String nameUser, pass;

        nameUser = edAccount.getText().toString().trim();
        pass = edPass.getText().toString().trim();

        if(nameUser.equals("") && pass.equals("")) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập tài khoản, mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if(checkEmail(nameUser)==true){
            checkMailCorrect(nameUser,pass);
            return;
        }
        if(checkPhoneNumber(nameUser)==true){
            checkPhoneCorrect(nameUser, pass);
            return ;
        }
        Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
    }
// Mail------------------------------------------------------------------------------
    public static boolean checkEmail(String email) {
    // Regular expression for basic email format
    String regex = "[^@]+@[^@]+\\.[^@]+";
    // Check if email matches the format
    if (!Pattern.matches(regex, email)) {
        return false;
    }
    // Check if email ends with "@gmail.com"
    return email.endsWith("@gmail.com");
}
    private void checkMailCorrect(String emailEnter, String passEnter){
        String query = "select Customer.idPowerCus, Staff.idPowerStaff from Customer, Staff";
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
        while (cursor.moveToNext()){
            int idPowerCus = cursor.getInt(0);
            int idPowerStaf = cursor.getInt(1);
            
            if(idPowerCus==0){
                checkMailCusCorrect(emailEnter,passEnter);
            }
            if (idPowerStaf==1) {
                checkMailSMCorrectDemo(emailEnter,passEnter);
            }
        }
    }
    private void checkMailSMCorrectDemo(String emailEnter, String passEnter){
        String query = "select * from Staff";
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
        while (cursor.moveToNext()){
            String email = cursor.getString(5).trim();
            String pass =cursor.getString(3).trim();
            if(emailEnter.equals(email)==true && passEnter.equals(pass)==true){
                    int idManager = cursor.getInt(6);
                if(idManager>0){
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    Staff staff = new Staff();
                    staff.setId_Staff(cursor.getInt(0));
                    staff.setName(cursor.getString(1));
                    staff.setPassWork(cursor.getString(3));
                    staff.setNumber_phone(cursor.getInt(4));
                    staff.setEmail(cursor.getString(5));
                    staff.setIdManager(cursor.getInt(6));
                    staff.setIdPowerStaff(cursor.getInt(8));
                    i.putExtra("Manager",staff);
                    startActivity(i);
                    finish();
                }else{
                    Staff staff = new Staff();
                    Intent i = new Intent(LoginActivity.this, PageStaffActivity.class);
                    staff.setId_Staff(cursor.getInt(0));
                    staff.setName(cursor.getString(1));
                    staff.setPassWork(cursor.getString(3));
                    staff.setNumber_phone(cursor.getInt(4));
                    staff.setEmail(cursor.getString(5));
                    staff.setIdManager(cursor.getInt(6));
                    staff.setIdPowerStaff(cursor.getInt(8));
                    i.putExtra("Staff",staff);
                    startActivity(i);
                    finish();
                }

            }
        }
    }

    private void checkMailCusCorrect(String emailEnter, String passEnter){
        String query = "select * from Customer";
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
        while (cursor.moveToNext()){
            String email = cursor.getString(3).trim();
            String pass =cursor.getString(2).trim();
            if(emailEnter.equals(email)==true && passEnter.equals(pass)==true){
                Customer customer = new Customer();
                Intent i = new Intent(LoginActivity.this, PageMainCus.class);
                customer.setId_cus(cursor.getInt(0));
                customer.setName_Cus( cursor.getString(1));
                customer.setPassword_Cus(cursor.getString(2));
                customer.setEmail(cursor.getString(3));
                customer.setNumberPhone(cursor.getInt(4));
                customer.setAddress(cursor.getString(5));
                customer.setId_card(cursor.getInt(6));
                customer.setIdPowerCus(cursor.getInt(7));
                customer.setImgCus(cursor.getBlob(8));
                i.putExtra("Customer",customer);
                startActivity(i);
                finish();
            }
        }
    }

// Phone------------------------------------------------------------------------------
    public static boolean checkPhoneNumber(String number) {
        Log.e("s","Check phone: "+number);
    // Kiểm tra độ dài chuỗi
    if (number.length() < 10 || number.length() > 10) {
        return false;
    }
    // Kiểm tra ký tự đầu tiên
    if (!Character.isDigit(number.charAt(0))) {
        return false;
    }
    // Kiểm tra các ký tự còn lại
    for (int i = 1; i < number.length(); i++) {
        if (!Character.isDigit(number.charAt(i)) && number.charAt(i) != '-') {
            return false;
        }
    }
    // Số điện thoại hợp lệ
    return true;
}
    private void checkPhoneCorrect(String emailEnter, String passEnter){
    String query = "select Customer.idPowerCus, Staff.idPowerStaff from Customer, Staff";
    Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
    while (cursor.moveToNext()){
        int idPowerCus = cursor.getInt(0);
        int idPowerStaf = cursor.getInt(1);

        if(idPowerCus==0){
            checkPhoneCusCorrect(emailEnter,passEnter);
        }
        if (idPowerStaf==1) {
            checkPhoneSMCorrect(emailEnter,passEnter);
        }
    }
}
    private void checkPhoneSMCorrect(String phoneEnter, String passEnter){
        String query = "select * from Staff";
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
        while (cursor.moveToNext()){

            String phone ="0"+ String.valueOf(cursor.getInt(4));
            String pass = cursor.getString(3).trim();
            if(phoneEnter.equals(phone)==true && passEnter.equals(pass)==true){
                int idManager = cursor.getInt(6);
                if(idManager>0){
                    Staff staff = new Staff();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    staff.setId_Staff(cursor.getInt(0));
                    staff.setName(cursor.getString(1));
                    staff.setPassWork(cursor.getString(3));
                    staff.setNumber_phone(cursor.getInt(4));
                    staff.setEmail(cursor.getString(5));
                    staff.setIdManager(cursor.getInt(6));
                    staff.setIdPowerStaff(cursor.getInt(8));
                    i.putExtra("Manager",staff);
                    startActivity(i);

                }else{
                    Staff staff = new Staff();
                    Intent i = new Intent(LoginActivity.this, PageStaffActivity.class);
                    staff.setId_Staff(cursor.getInt(0));
                    staff.setName(cursor.getString(1));
                    staff.setPassWork(cursor.getString(3));
                    staff.setNumber_phone(cursor.getInt(4));
                    staff.setEmail(cursor.getString(5));
                    staff.setIdManager(cursor.getInt(6));
                    staff.setIdPowerStaff(cursor.getInt(8));
                    i.putExtra("Staff",staff);
                    startActivity(i);
                }
            }
        }
    }
    private void checkPhoneStaffCorrect(String phoneEnter, String passEnter){
        String query = "select * from Staff";
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
        while (cursor.moveToNext()){
            String phone = String.valueOf(cursor.getInt(4));
            String pass = cursor.getString(3).trim();
            if(phoneEnter.equals(phone)==true && passEnter.equals(pass)==true){
                Staff staff = new Staff();
                Intent i = new Intent(LoginActivity.this, PageMainCus.class);
                staff.setId_Staff(cursor.getInt(0));
                staff.setName(cursor.getString(1));
                staff.setPassWork(cursor.getString(3));
                staff.setNumber_phone(cursor.getInt(4));
                staff.setEmail(cursor.getString(5));
                staff.setIdManager(cursor.getInt(6));
                staff.setIdPowerStaff(cursor.getInt(8));
                i.putExtra("c",staff);
                startActivity(i);
            }
        }
    }
    private void checkPhoneCusCorrect(String phoneEnter, String passEnter){
        String query = "select * from Customer";
        Cursor cursor = PageMainLoginActivity.database.rawQuery(query,null);
        while (cursor.moveToNext()){
            String phone = "0"+String.valueOf(cursor.getInt(4));

            String pass = cursor.getString(2).trim();
            if(phoneEnter.equals(phone)==true && passEnter.equals(pass)==true){
                Log.e("s","Phone enter: "+passEnter+"\n Phone data: "+phone);
                Customer customer = new Customer();
                Intent i = new Intent(LoginActivity.this, PageMainCus.class);
                customer.setId_cus(cursor.getInt(0));
                customer.setName_Cus(cursor.getString(1));
                customer.setPassword_Cus(cursor.getString(2));
                customer.setEmail(cursor.getString(3));
                customer.setNumberPhone(cursor.getInt(4));
                customer.setAddress(cursor.getString(5));
                customer.setIdPowerCus(cursor.getInt(7));
                customer.setImgCus(cursor.getBlob(8));
                i.putExtra("Customer",customer);
                startActivity(i);
            }
        }
    }
    //--------------------------------------------------------------------------------
    private void addEvent() {
        clickBack();
        eventShowPass();
        clickRegister();
        clickForgot();
    }
    private void clickForgot() {
        btRePass = findViewById(R.id.buttonForgotPass_Login);
//        btRePass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(LoginActivity.this, ForgotPassActivity.class);
//                startActivity(i);
//            }
//        });
    }
    private void clickRegister() {
        btRegister = findViewById(R.id.buttonRegister_Login);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    private void eventShowPass() {
        edPass = findViewById(R.id.editTextPass_Login);
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
    }
    private void clickBack() {
        btBack = findViewById(R.id.buttonBack_Login);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, PageMainLoginActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("s","Da destroy LoginActivity");
    }
}