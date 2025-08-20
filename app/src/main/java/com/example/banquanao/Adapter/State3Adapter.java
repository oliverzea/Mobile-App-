package com.example.banquanao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.database.Order;

import java.util.List;

public class State3Adapter extends  RecyclerView.Adapter<State3Adapter.State3ViewHolder>{
    List<Order> mlist3;
    Context context;

    public State3Adapter(List<Order> mlist3, Context context) {
        this.mlist3 = mlist3;
        this.context = context;
    }

    @NonNull
    @Override
    public State3ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_state3,parent,false);

        return new State3ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull State3ViewHolder holder, int position) {
        Order order = mlist3.get(position);
        holder.txtHeader.setText("Đơn hàng "+order.getId_Order());
        holder.txtState.setText("Đang giao hàng");
        holder.txtName.setText(order.getCustomer().getName_Cus());
        holder.txtHeader.setText("Đơn hàng "+order.getId_Order());
        holder.txtPhone.setText(order.getCustomer().getNumberPhone()+"");
        holder.txtEmail.setText(order.getCustomer().getEmail());
        holder.txtAddr.setText(order.getCustomer().getAddress());
    }

    @Override
    public int getItemCount() {
        if(mlist3.size()>0){
            return mlist3.size();
        }
        return 0;
    }

    public class State3ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPhone,txtName,txtAddr,txtEmail,txtState,txtHeader;
        LinearLayout linearLayout;
        public State3ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtCusNameState3);
            txtAddr=itemView.findViewById(R.id.txtCusAddressState3);
            txtPhone=itemView.findViewById(R.id.txtCusPhoneState3);
            txtEmail=itemView.findViewById(R.id.txtCusEmailState3);
            txtState=itemView.findViewById(R.id.txtState3);
            txtHeader=itemView.findViewById(R.id.txtHeaderS3);
        }
    }
}
