package com.example.banquanao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Activity.State1DetailActivity;
import com.example.banquanao.database.Order;

import java.util.List;

public class State1Adapter extends RecyclerView.Adapter<State1Adapter.WaitingViewHolder> {
    List<Order> mlistWaiting;
    Context context;

    public State1Adapter(List<Order> mlistWaiting, Context context) {
        this.mlistWaiting = mlistWaiting;
        this.context = context;
    }

    @NonNull
    @Override
    public WaitingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_state1,parent,false);
        return new WaitingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingViewHolder holder, int position) {
        Order order=mlistWaiting.get(position);
        holder.txtName.setText(order.getCustomer().getName_Cus());
        holder.txtEmail.setText(order.getCustomer().getEmail());
        holder.txtPhone.setText(order.getCustomer().getNumberPhone()+"");
        holder.txtAddr.setText(order.getCustomer().getAddress());
        holder.txtState.setText(order.getId_State()+"");
        holder.txtHeader.setText("Đơn hàng "+order.getId_Order()+"");

        //thực hiện click để chuyển sang trang chi tiết
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, State1DetailActivity.class);
                intent.putExtra("id_order",order.getId_Order());
                intent.putExtra("Customer",order.getCustomer());
                intent.putExtra("id_State",order.getId_State());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mlistWaiting.size()>0){
            return mlistWaiting.size();
        }
        return 0;
    }

    public class WaitingViewHolder extends RecyclerView.ViewHolder {
        TextView txtPhone,txtName,txtAddr,txtEmail,txtState,txtHeader;
        LinearLayout linearLayout;
        public WaitingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtCusNameState1);
            txtAddr=itemView.findViewById(R.id.txtCusAddressState1);
            txtPhone=itemView.findViewById(R.id.txtCusPhoneState1);
            txtEmail=itemView.findViewById(R.id.txtCusEmailState1);
            txtState=itemView.findViewById(R.id.txtState1);
            txtHeader=itemView.findViewById(R.id.txtHeaderS1);
            linearLayout = itemView.findViewById(R.id.clickDetailSate1);

        }
    }
}
