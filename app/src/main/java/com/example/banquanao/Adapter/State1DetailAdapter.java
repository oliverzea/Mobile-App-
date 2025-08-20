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

import com.example.banquanao.R;
import com.example.banquanao.Activity.HomeActivity;
import com.example.banquanao.database.Product;

import java.util.List;

public class State1DetailAdapter extends RecyclerView.Adapter<State1DetailAdapter.State1DetailViewHolder> {

    List<Product> mlist;
    Context context ;

    public State1DetailAdapter(List<Product> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }

    @NonNull
    @Override
    public State1DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_detail_state1,parent,false);
        // View view = LayoutInflater.from(context).inflate(R.layout.item_detail_state1,parent,false);
        return new State1DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull State1DetailViewHolder holder, int position) {
        Product pro = mlist.get(position);
        holder.txtNamePro.setText(pro.getName());
        holder.txtQuantity.setText(pro.getQuantity()+"");
        holder.txtPricePro.setText(pro.getPrice()+"");
        holder.txtSize.setText(pro.getSize()+"");
        double total = pro.getPrice()*pro.getQuantity();
        holder.txtTotal.setText(total+"");
        Bitmap bitmap = HomeActivity.TranslateByteToBitmap(pro.getIdResourse());
        holder.img.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        if(mlist.size()>0){
            return mlist.size();
        }
        return 0;
    }

    public class State1DetailViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamePro,txtPricePro,txtQuantity,txtTotal,txtSize;
        ImageView img ;
        public State1DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamePro = itemView.findViewById(R.id.txtNameProDetailState1);
            txtPricePro = itemView.findViewById(R.id.txtPriceDetailState1);
            txtQuantity = itemView.findViewById(R.id.txtQuantityDetailState1);
            txtTotal = itemView.findViewById(R.id.txtTotalDetailState1);
            txtSize = itemView.findViewById(R.id.txtSizeDetailState1);
            img = itemView.findViewById(R.id.imageProDetailState1);


        }
    }
}
