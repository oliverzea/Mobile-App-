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

import com.example.banquanao.Activity.SuccessActivity;
import com.example.banquanao.R;
import com.example.banquanao.database.Order;

import java.util.List;

public class AdapterSuccess extends RecyclerView.Adapter<AdapterSuccess.SuccessViewHolder> {
    List<Order> mlistSuccess;
    Context context;

public AdapterSuccess(List<Order> mlistSuccess, Context context){
    this.mlistSuccess = mlistSuccess;
    this.context = context;
}
    @NonNull
    @Override
    public SuccessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_success,parent, false);
        return new SuccessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuccessViewHolder holder, int position) {
        Order order = mlistSuccess.get(position);
        holder.txtNameCusSuc.setText(order.getCustomer().getName_Cus()+"");
        holder.txtEmailCusSuc.setText(order.getCustomer().getEmail()+"");
        holder.txtNumberCusSuc.setText(order.getCustomer().getNumberPhone()+"");
        holder.txtDonhangSuccess.setText("Đơn hàng thứ "+ order.getId_Order());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuccessActivity.class);
                intent.putExtra("Customer",order.getCustomer());
                intent.putExtra("idOrder",order.getId_Order());
                intent.putExtra("idState",order.getId_State());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mlistSuccess.size()>0){
            return mlistSuccess.size();
        }
        return 0;
    }
    public class SuccessViewHolder extends RecyclerView.ViewHolder{

    TextView txtNameCusSuc,txtEmailCusSuc, txtNumberCusSuc, txtDonhangSuccess;
    LinearLayout linearLayout;

        public SuccessViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameCusSuc = itemView.findViewById(R.id.txtNameCusSuc);
            txtEmailCusSuc = itemView.findViewById(R.id.txtEmailCusSuc);
            txtNumberCusSuc = itemView.findViewById(R.id.txtNumberCusSuc);
            txtDonhangSuccess= itemView.findViewById(R.id.txtDonhangSuccess);
            linearLayout = itemView.findViewById(R.id.ClickSuccess);

        }
    }
}
