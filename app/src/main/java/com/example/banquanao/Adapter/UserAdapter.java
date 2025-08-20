package com.example.banquanao.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Fragment.UserFragment;
import com.example.banquanao.database.Customer;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHold>{

    UserFragment context;
    List<Customer> list_Cus;

    public UserAdapter(UserFragment context, ArrayList<Customer> list_Cus){
        this.context=context;
        this.list_Cus=list_Cus;

    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context.getContext());
        View viewCus=layoutInflater.inflate(R.layout.item_cus,parent,false);
        ViewHold viewHolderCus=new ViewHold(viewCus);
        return viewHolderCus;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Customer customer=list_Cus.get(position);
        holder.txtNameCus.setText(customer.getName_Cus());
        holder.txtIdCus.setText(customer.getId_cus()+"");
        holder.txtAddress.setText(customer.getAddress());
        holder.txtEmail.setText(customer.getEmail());
        holder.txtPhone.setText(customer.getNumberPhone()+"");
        holder.txtPass.setText(customer.getPassword_Cus());
        holder.txtCard.setText(customer.getId_card()+"");
    }


    @Override
    public int getItemCount() {
        if(list_Cus.size()<=0){
            return 0;
        }
        return list_Cus.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {


        TextView txtNameCus, txtIdCus, txtEmail, txtPhone, txtPass,txtAddress,txtCard;


        public ViewHold(@NonNull View itemView) {
            super(itemView);
            txtNameCus = itemView.findViewById(R.id.txtNameCus);
            txtIdCus = itemView.findViewById(R.id.txtIdCus);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtPass = itemView.findViewById(R.id.txtPass);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtCard = itemView.findViewById(R.id.txtidCard);
        }


    }
}
