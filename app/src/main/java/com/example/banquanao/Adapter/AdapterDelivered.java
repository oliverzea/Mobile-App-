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

import com.example.banquanao.Activity.DeliveryDetailActivity;
import com.example.banquanao.R;
import com.example.banquanao.database.Order;
import com.example.banquanao.database.Order;

import java.util.List;

public class AdapterDelivered extends RecyclerView.Adapter<AdapterDelivered.deliveredViewHolder> {

    List<Order> mlist;
    Context context;


    public AdapterDelivered(List<Order> mlist, Context context){
        this.mlist = mlist;
        this.context=context;
    }

    @NonNull
    @Override
    public deliveredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delivered, parent,false);
        return new deliveredViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull deliveredViewHolder holder, int position) {
        Order order = mlist.get(position);
        holder.txtNameCusDeli.setText(order.getCustomer().getName_Cus()+"");
        holder.txtEmailCusDeli.setText(order.getCustomer().getEmail()+"");
        holder.txtNumberCusDeli.setText(order.getCustomer().getNumberPhone()+"");
        holder.txtDonhangDeli.setText("Đơn hàng thứ " + order.getId_Order());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeliveryDetailActivity.class);
                intent.putExtra("Customer",order.getCustomer());
                intent.putExtra("idOrder",order.getId_Order());
                intent.putExtra("idState",order.getId_State());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mlist.size()>0){
            return mlist.size();
        }
        return 0;
    }


    public class deliveredViewHolder extends RecyclerView.ViewHolder {
        TextView txtDonhangDeli, txtNameCusDeli,txtEmailCusDeli,txtNumberCusDeli;
        LinearLayout linearLayout;
        public deliveredViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDonhangDeli = itemView.findViewById(R.id.txtDonhangDeli);
            txtNameCusDeli = itemView.findViewById(R.id.txtNameCusDeli);
            txtEmailCusDeli = itemView.findViewById(R.id.txtEmailCusDeli);
            txtNumberCusDeli = itemView.findViewById(R.id.txtNumberCusDeli);
            linearLayout = itemView.findViewById(R.id.ClickDeli);
        }
    }
}
