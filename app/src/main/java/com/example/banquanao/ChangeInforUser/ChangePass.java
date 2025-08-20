package com.example.banquanao.ChangeInforUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;

public class ChangePass extends AppCompatActivity {
    EditText passOld,passNew,rePass;
    Button btnConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        addControls();
        authorPass();
    }

    private void authorPass() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            String oldPass , newPass , Repass;
            @Override
            public void onClick(View v) {
                oldPass = passOld.getText().toString().trim();
                newPass = passNew.getText().toString().trim();
                Repass = rePass.getText().toString().trim();

                if(oldPass==null) {
                    PageMainCus.MyToast(getApplicationContext(), "Vui long nhập mật khẩu cũ !");
                    return;
                }
                if(newPass==null) {
                    PageMainCus.MyToast(getApplicationContext(), "Vui long nhập mật khẩu mới !");
                    return;
                }
                if(Repass==null) {
                    PageMainCus.MyToast(getApplicationContext(), "Vui long nhập lại mật khẩu !" );
                    return;
                }
                Log.e("Xem thong tin customer", PageMainCus.customer.getPassword_Cus()+"\n"+oldPass+"\n"+newPass+"\n"+Repass+"\n");
                if(PageMainCus.customer.getPassword_Cus().trim().compareTo(oldPass)==0 && newPass.compareTo(Repass)==0){
                    PageMainCus.customer.setPassword_Cus(newPass);
                    ContentValues contentValues= new ContentValues();
                    contentValues.put("passWork_cus", PageMainCus.customer.getPassword_Cus());
                    long row= PageMainCus.database.update(PageMainCus.table_user,contentValues,"id_Customer=?",new String[]{String.valueOf(PageMainCus.customer.getId_cus())});
                    if(row<0){
                        Log.e("Kiem tra","Ko cap nhat duoc ");
                    }else{
                        Log.e("Kiem tra","cap nhat duoc ");
                    }
                    finish();
                    Log.e("Pass", PageMainCus.customer.getPassword_Cus());

                }else{
                    PageMainCus.MyToast(getApplicationContext(),"Thay đổi không thành công !");

                }
            }
        });
    }

    private void addControls() {
        passOld = findViewById(R.id.passOld);
        passNew=findViewById(R.id.passNew);
        rePass=findViewById(R.id.rePass);
        btnConfirm = findViewById(R.id.confirmPass);
        setSupportActionBar(findViewById(R.id.changePassToolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==android.R.id.home){
           finish();
       }
        return super.onOptionsItemSelected(item);
    }
}