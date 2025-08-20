package com.example.banquanao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.database.Order;

import java.util.List;

public class State2Adapter extends  RecyclerView.Adapter<State2Adapter.State2ViewHolder>{

    List<Order> mlist ;
    Context context;

    public State2Adapter(List<Order> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }

    @NonNull
    @Override
    public State2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_state2,parent,false);
        return new State2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull State2ViewHolder holder, int position) {
        Order order= mlist.get(position);
        holder.txtState.setText("  Đã xác nhận");
        holder.txtName.setText(order.getCustomer().getName_Cus());
        holder.txtHeader.setText("Đơn hàng "+order.getId_Order());
        holder.txtNumberPhone.setText(order.getCustomer().getNumberPhone()+"");
        holder.txtEmail.setText(order.getCustomer().getEmail());
        holder.txtAddress.setText(order.getCustomer().getAddress());


    }

    @Override
    public int getItemCount() {
        if(mlist.size()>0)
            return mlist.size();
        return 0;
    }

    public class State2ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtEmail,txtNumberPhone,txtAddress,txtState,txtHeader;
        public State2ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtCusNameState2);
            txtEmail = itemView.findViewById(R.id.txtCusEmailState2);
            txtNumberPhone = itemView.findViewById(R.id.txtCusPhoneState2);
            txtHeader = itemView.findViewById(R.id.txtHeaderS2);
            txtAddress = itemView.findViewById(R.id.txtCusAddressState2);
            txtState = itemView.findViewById(R.id.txtState2);
        }
    }
}
