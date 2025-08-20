package com.example.banquanao.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.banquanao.ChangeInforUser.ChangeInformationUser;
import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;
import com.example.banquanao.database.Customer;


public class InforFragment extends Fragment {
    TextView txtName , txtEmail,txtAddress,txtNumber;
    ImageView imgUser;
    Context context;
    Button btnChangeInfor;
    public static final int REQUEST_CODE=100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_infor, container, false);
        addControls(view);
        addData();
        handleButtonChangeInfor();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("S","onStart infor");
        addData();
    }

    private void handleButtonChangeInfor() {
        btnChangeInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ChangeInformationUser.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    private void addData() {
        txtName.setText("Tên: "+ PageMainCus.customer.getName_Cus());
        txtEmail.setText("Email: "+ PageMainCus.customer.getEmail());
        txtAddress.setText("Địa chỉ: "+ PageMainCus.customer.getAddress());
        txtNumber.setText("Số điện thoại: "+ PageMainCus.customer.getNumberPhone());
        imgUser.setImageBitmap(PageMainCus.TranslateByteToBitmap(PageMainCus.customer.getImgCus()));
    }


    private void addControls(View view) {
        txtName = view.findViewById(R.id.nameUser);
        txtEmail = view.findViewById(R.id.emailUser);
        txtAddress = view.findViewById(R.id.addressUser);
        txtNumber = view.findViewById(R.id.numberUser);
        imgUser = view.findViewById(R.id.imgInfor);
        btnChangeInfor = view.findViewById(R.id.changeInforUser);
        context = view.getContext();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE&&resultCode==RESULT_OK){
            if(data.hasExtra("customer")){
                Customer customer= (Customer) data.getSerializableExtra("customer");
                PageMainCus.updateUser(customer);
                if( PageMainCus.customer.getImgCus()!=null){
                    imgUser.setImageBitmap(PageMainCus.TranslateByteToBitmap( PageMainCus.customer.getImgCus()));

                }
                PageMainCus.setUser(customer);

            }else{
                PageMainCus.MyToast(getContext(),"Ko lay dc ");
            }
        }else{
            PageMainCus.MyToast(getContext(),"Ko lay dc ");
        }
    }
}