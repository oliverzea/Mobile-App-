package com.example.banquanao.ChangeInforUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChangeCard extends AppCompatActivity {
    EditText editNumber,editDate,editCVV;
    Button btnComfirmCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_card);
        addControls();
        addButton();
    }

    private void addButton() {
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDay();
            }
        });
        btnComfirmCard.setOnClickListener(new View.OnClickListener() {
            int numberCard,CVV;
            String date;
            @Override
            public void onClick(View v) {


                if(editNumber.getText().toString().trim().isEmpty()){
                    PageMainCus.MyToast(getApplicationContext(),"Nhập số thẻ");
                    return;
                }

                if(editDate.getText().toString().trim().isEmpty()){
                    PageMainCus.MyToast(getApplicationContext(),"Chọn ngày cấp thẻ");
                    return;
                }
                if(editCVV.getText().toString().trim().isEmpty()){
                    PageMainCus.MyToast(getApplicationContext(),"Nhập số CVV");
                    return;
                }
                numberCard= Integer.parseInt(editNumber.getText().toString().trim());
                CVV= Integer.parseInt(editCVV.getText().toString().trim());
                if(checkCard()==true){
                    updateData(numberCard,editDate.getText().toString().trim(),CVV);
                    PageMainCus.card.setNumber_Cart(numberCard);
                    PageMainCus.card.setDate_issue(editDate.getText().toString().trim());
                    PageMainCus.card.setNumberCVV(CVV);
                }else{
                    insertCard(numberCard,editDate.getText().toString().trim(),CVV);
                    PageMainCus.card.setNumber_Cart(numberCard);
                    PageMainCus.card.setDate_issue(editDate.getText().toString().trim());
                    PageMainCus.card.setNumberCVV(CVV);
                    PageMainCus.customer.setId_card(getMaxIdCard());
                }

                finish();
            }
        });
    }

    private void updateData(int num,String date,int CVV){

        ContentValues contentValues = new ContentValues();
        contentValues.put("number_Cart",num);
        contentValues.put("date_issue",date);
        contentValues.put("numberCVV",CVV);
        long row= PageMainCus.database.update(PageMainCus.table_card,contentValues,"id_Cart=?",new String[]{"1"});
        if(row<0){
            PageMainCus.MyToast(getApplicationContext(),"Khong cap nhat duoc");
        }else{
            PageMainCus.MyToast(getApplicationContext()," Cap nhat thanh cong");
        }
    }
    private void insertCard(int num,String date,int CVV){

        ContentValues contentValues = new ContentValues();
        contentValues.put("id_Cart",getMaxIdCard());
        contentValues.put("number_Cart",num);
        contentValues.put("date_issue",date);
        contentValues.put("numberCVV",CVV);

        ContentValues valuesCusCard = new ContentValues();
        valuesCusCard.put("id_Card",getMaxIdCard());
        int resultUpdate = PageMainCus.database.update(PageMainCus.table_user,valuesCusCard,"id_Customer=?",new String[]{String.valueOf(PageMainCus.customer.getId_cus())});

        long row= PageMainCus.database.insert(PageMainCus.table_card,null,contentValues);
        if(row<0){
            PageMainCus.MyToast(getApplicationContext(),"Them khong thanh cong");
        }else{
            PageMainCus.MyToast(getApplicationContext()," Them thanh cong");
        }
    }
    private  void SetDay(){
        Calendar calendar =Calendar.getInstance();
        int ngay =calendar.get(Calendar.DATE);
        int thang=calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog  = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(year,month,dayOfMonth);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    editDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    private void addControls() {
        editNumber= findViewById(R.id.numberCard);
        editDate= findViewById(R.id.dateIssue);
        editCVV= findViewById(R.id.numberCVV);
        btnComfirmCard= findViewById(R.id.confirmCard);
        setSupportActionBar(findViewById(R.id.changeCardToolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean checkCard(){
        String query = "select id_Card from Customer where id_Customer =?";
        Cursor c = PageMainCus.database.rawQuery(query,new String[]{String.valueOf(PageMainCus.customer.getId_cus())});
        while (c.moveToNext()){
            int idCard = c.getInt(0);
            if(idCard<=0){
                return false;
            }
        }

        return true;
    }

    private  int getMaxIdCard(){
        String query = "select id_Cart from Cart ";
        int max = -1;
        Cursor c = PageMainCus.database.rawQuery(query,null);
        while (c.moveToNext()){
            int idCard = c.getInt(0);
            if(max <idCard){
                max= idCard;
            }
        }
        return max+1;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}