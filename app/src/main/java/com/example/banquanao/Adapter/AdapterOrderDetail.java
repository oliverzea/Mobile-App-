package com.example.banquanao.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.Activity.PageStaffActivity;
import com.example.banquanao.R;
import com.example.banquanao.database.Product;

import java.util.List;

public class AdapterOrderDetail extends RecyclerView.Adapter<AdapterOrderDetail.OrderDetailViewHolder>{

    List<Product> mlist;
    Context context;

    public AdapterOrderDetail(List<Product> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail,parent,false);

        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        Product product = mlist.get(position);
        Bitmap bm= PageStaffActivity.TranslateByteToBitmap(product.getIdResourse());
        holder.img.setImageBitmap(bm);
        holder.txtName.setText(product.getName());
        holder.txtTotal.setText(product.getTotal()+"");
        holder.txtPrice.setText(product.getPrice()+"");
        holder.txtQuantity.setText(product.getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        if(mlist.size()>0){
            return mlist.size();
        }
        return 0;
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtPrice,txtQuantity,txtTotal;
        ImageView img;
        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNamePro);
            img= itemView.findViewById(R.id.imgPro);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtQuantity= itemView.findViewById(R.id.txtQuantityNPro);
            txtPrice = itemView.findViewById(R.id.txtPricePro);
        }
    }
}
