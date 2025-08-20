package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banquanao.R;
import com.example.banquanao.database.Staff;

public class EditInfoActivity extends AppCompatActivity {

    EditText edtTenNvUpdate,edtPasswordUpdate,edtNumPhoneUpdate,edtEmailUpdate;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        addControl();
        UpdateInfo();
    }

    private void UpdateInfo() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edtTenNvUpdate.getText().toString().trim();
                String passWork = edtPasswordUpdate.getText().toString().trim();
                int number = Integer.parseInt(edtNumPhoneUpdate.getText().toString().trim());
                String email = edtEmailUpdate.getText().toString().trim();

                if(name.equals("")||passWork.equals("")||edtNumPhoneUpdate.getText().toString().trim().equals("")||email.equals("")){
                    Toast.makeText(EditInfoActivity.this,"Nhập đầy đủ thông tin !",Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues values = new ContentValues();

                values.put("name",name);
                values.put("passWork", passWork);
                values.put("number_phone", number);
                values.put("email", email);

                int kq= PageStaffActivity.database.update("Staff", values,"id_Staff=?",
                        new String[]{String.valueOf(PageStaffActivity.staff.getId_Staff())});
                if(kq>0){
                    int id = PageStaffActivity.staff.getId_Staff();
                    PageStaffActivity.staff = new Staff(id,name,passWork,number,email);
                    Toast.makeText(EditInfoActivity.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditInfoActivity.this,"Cập nhật không thành công",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }


    private void addControl() {
        //edtMaNvUpdate =findViewById(R.id.edtMaNvUpdate);
        edtTenNvUpdate = findViewById(R.id.edtTenNvUpdate);
        edtPasswordUpdate = findViewById(R.id.edtPasswordUpdate);
        edtNumPhoneUpdate = findViewById(R.id.edtNumPhoneUpdate);
        edtEmailUpdate = findViewById(R.id.edtEmailUpdate);
        btnUpdate = findViewById(R.id.btnUpdate);

        //edtMaNvUpdate.setEnabled(false);
        edtTenNvUpdate.setText(PageStaffActivity.staff.getName());
        edtPasswordUpdate.setText(PageStaffActivity.staff.getPassWork());
        edtNumPhoneUpdate.setText(PageStaffActivity.staff.getNumber_phone()+"");
        edtEmailUpdate.setText(PageStaffActivity.staff.getEmail());

    }
}