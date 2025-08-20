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

import com.example.banquanao.Activity.OrderDetailActivity;
import com.example.banquanao.R;
import com.example.banquanao.database.Order;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.orderViewHolder>{
    List<Order> mlist;
    Context context;

    public AdapterOrder(List<Order> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);

        return new orderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        Order order = mlist.get(position);
        holder.name.setText(order.getCustomer().getName_Cus()+" ");
        holder.sdt.setText(order.getCustomer().getNumberPhone()+" ");
        holder.header.setText("Đơn hàng thứ "+order.getId_Order());
        holder.email.setText(order.getCustomer().getEmail());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("Customer",order.getCustomer());
                intent.putExtra("idOrder",order.getId_Order());
                intent.putExtra("idState",order.getId_State());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mlist.size()>0)
            return mlist.size();
        return 0;
    }

    public class orderViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,sdt,header;
        LinearLayout linearLayout ;
        public orderViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.txtNameCus);
            email =itemView.findViewById(R.id.txtNameEmail);
            sdt =itemView.findViewById(R.id.txtSdt);
            header =itemView.findViewById(R.id.txtDonhang);
            linearLayout= itemView.findViewById(R.id.Click);
        }
    }
}
