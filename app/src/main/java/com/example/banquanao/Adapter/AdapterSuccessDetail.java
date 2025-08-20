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

public class AdapterSuccessDetail extends RecyclerView.Adapter<AdapterSuccessDetail.SuccessDetailViewHolder>{
    List<Product> mlistSuc;
    Context context;
    public AdapterSuccessDetail(List<Product> mlistSuc, Context context){
        this.mlistSuc = mlistSuc;
        this.context = context;
    }

    @NonNull
    @Override
    public SuccessDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_success_detail,parent,false);

        return new SuccessDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuccessDetailViewHolder holder, int position) {
        Product product = mlistSuc.get(position);
        Bitmap bm= PageStaffActivity.TranslateByteToBitmap(product.getIdResourse());
        holder.imgProSuccess.setImageBitmap(bm);
        holder.txtNameProSuccess.setText(product.getName());
        holder.txtTotalSuccess.setText(product.getTotal()+"");
        holder.txtPriceProSuccess.setText(product.getPrice()+"");
        holder.txtQuantityNProSuccess.setText(product.getQuantity()+"");
    }

    @Override
    public int getItemCount() {
        if(mlistSuc.size()>0){
            return mlistSuc.size();
        }
        return 0;
    }

    public class SuccessDetailViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameProSuccess,txtPriceProSuccess,txtQuantityNProSuccess,txtTotalSuccess;
        ImageView imgProSuccess;
        public SuccessDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameProSuccess = itemView.findViewById(R.id.txtNameProSuccess);
            imgProSuccess= itemView.findViewById(R.id.imgProSuccess);
            txtTotalSuccess = itemView.findViewById(R.id.txtTotalSuccess);
            txtQuantityNProSuccess= itemView.findViewById(R.id.txtQuantityNProSuccess);
            txtPriceProSuccess = itemView.findViewById(R.id.txtPriceProSuccess);
        }
    }
}
