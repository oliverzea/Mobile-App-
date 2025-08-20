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

public class State4Adatapter extends RecyclerView.Adapter<State4Adatapter.State4ViewHolder>{

    List<Order> mlist4;
    Context context;

    public State4Adatapter(List<Order> mlist4, Context context) {
        this.mlist4 = mlist4;
        this.context = context;
    }

    @NonNull
    @Override
    public State4ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_state4,parent,false);


        return new State4ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull State4ViewHolder holder, int position) {
        Order order = mlist4.get(position);
        holder.txtHeader.setText("Đơn hàng "+order.getId_Order());
        holder.txtState.setText("Đơn hàng thành công");
        holder.txtName.setText(order.getCustomer().getName_Cus());
        holder.txtHeader.setText("Đơn hàng "+order.getId_Order());
        holder.txtPhone.setText(order.getCustomer().getNumberPhone()+"");
        holder.txtEmail.setText(order.getCustomer().getEmail());
        holder.txtAddr.setText(order.getCustomer().getAddress());
    }

    @Override
    public int getItemCount() {
        if(mlist4.size()>0)
            return mlist4.size();
        return 0;
    }

    public class State4ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPhone,txtName,txtAddr,txtEmail,txtState,txtHeader;
        LinearLayout linearLayout;
        public State4ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtCusNameState4);
            txtAddr=itemView.findViewById(R.id.txtCusAddressState4);
            txtPhone=itemView.findViewById(R.id.txtCusPhoneState4);
            txtEmail=itemView.findViewById(R.id.txtCusEmailState4);
            txtState=itemView.findViewById(R.id.txtState4);
            txtHeader=itemView.findViewById(R.id.txtHeaderS4);
        }
    }
}
