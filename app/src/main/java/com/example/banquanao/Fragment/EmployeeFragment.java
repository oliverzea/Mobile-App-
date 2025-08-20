package com.example.banquanao.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.banquanao.Activity.EditInfoActivity;
import com.example.banquanao.Activity.PageStaffActivity;
import com.example.banquanao.R;


public class EmployeeFragment extends Fragment {
    ImageView ivNV;
    TextView txtTenNV, txtMaNV, txtPassword, txtNumPhone, txtEmail;
    Button btnEdit;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_employee, container, false);
        addControl(view);

        txtMaNV = view.findViewById(R.id.txtMaNV);
        txtTenNV = view.findViewById(R.id.txtTenNV);
        txtPassword =view.findViewById(R.id.txtPassword);
        txtNumPhone = view.findViewById(R.id.txtNumPhone);
        txtEmail = view.findViewById(R.id.txtEmail);

        txtMaNV.setText(PageStaffActivity.staff.getId_Staff()+"");
        txtTenNV.setText(PageStaffActivity.staff.getName());
        txtPassword.setText(PageStaffActivity.staff.getPassWork());
        txtNumPhone.setText(PageStaffActivity.staff.getNumber_phone()+"");
        txtEmail.setText(PageStaffActivity.staff.getEmail());
        addEvent();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        txtMaNV.setText(PageStaffActivity.staff.getId_Staff()+"");
        txtTenNV.setText(PageStaffActivity.staff.getName());
        txtPassword.setText(PageStaffActivity.staff.getPassWork());
        txtNumPhone.setText(PageStaffActivity.staff.getNumber_phone()+"");
        txtEmail.setText(PageStaffActivity.staff.getEmail());
    }

    private void addEvent() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EditInfoActivity.class);

                startActivity(intent);
            }
        });
    }

    private void addControl(View view) {
        ivNV = view.findViewById(R.id.ivNV);
        txtTenNV = view.findViewById(R.id.txtTenNV);
        txtMaNV = view.findViewById(R.id.txtMaNV);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtNumPhone = view.findViewById(R.id.txtNumPhone);
        txtEmail = view.findViewById(R.id.txtEmail);
        btnEdit = view.findViewById(R.id.btnEdit);
    }
}