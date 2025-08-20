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

public class AdapterDeliveredDetail extends RecyclerView.Adapter<AdapterDeliveredDetail.DeliveredViewHolder> {
    List<Product> mlist;
    Context context;
    public AdapterDeliveredDetail(List<Product> mlist, Context context){
        this.mlist = mlist;
        this.context = context;
    }

    @NonNull
    @Override
    public DeliveredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delivered_detail, parent,false);
        return new DeliveredViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredViewHolder holder, int position) {
        Product product = mlist.get(position);
        Bitmap bm= PageStaffActivity.TranslateByteToBitmap(product.getIdResourse());
        holder.imgProDeliDetail.setImageBitmap(bm);
        holder.txtNameProDeli.setText(product.getName());
        holder.txtTotalDeli.setText(product.getTotal()+"");
        holder.txtPriceProDeli.setText(product.getPrice()+"");
        holder.txtQuantityNProDeli.setText(product.getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        if(mlist.size()>0){
            return mlist.size();
        }
        return 0;
    }

    public class DeliveredViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameProDeli,txtPriceProDeli,txtQuantityNProDeli,txtTotalDeli;
        ImageView imgProDeliDetail;
        public DeliveredViewHolder(@NonNull View itemView) {

            super(itemView);
            txtNameProDeli= itemView.findViewById(R.id.txtNameProDeli);
            txtPriceProDeli= itemView.findViewById(R.id.txtPriceProDeli);
            txtQuantityNProDeli= itemView.findViewById(R.id.txtQuantityNProDeli);
            txtTotalDeli= itemView.findViewById(R.id.txtTotalDeli);
            imgProDeliDetail = itemView.findViewById(R.id.imgProDeliDetail);

        }
    }
}
