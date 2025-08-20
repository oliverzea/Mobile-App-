package com.example.banquanao.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.database.Cart;
import com.example.banquanao.R;

import java.util.List;

public class OrderRecyclerview extends RecyclerView.Adapter<OrderRecyclerview.OrderViewHolder>{

    List<Cart> mlist;
    Context mcontext;
    public OrderRecyclerview(List<Cart> mlist,Context context) {
        this.mlist = mlist;
        this.mcontext =context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_product,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Cart cart = mlist.get(position);
        Bitmap bitmap = PageMainCus.TranslateByteToBitmap(cart.getImg());
        holder.imgProduct.setImageBitmap(bitmap);
        holder.name.setText(cart.getName());
        holder.price.setText(cart.getPrice()+"");
        holder.quatity.setText(cart.getQuantity_cart()+"");
        double total = cart.getPrice()*cart.getQuantity_cart();
        holder.total_price.setText(total+"");
    }

    @Override
    public int getItemCount() {
      if(mlist!=null){
          return mlist.size();
      }

      return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView  name,quatity,price,total_price;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgOrderProduct);
            name = itemView.findViewById(R.id.nameProductOrder);
            quatity = itemView.findViewById(R.id.quatityProductOrder);
            price = itemView.findViewById(R.id.priceProductOrder);
            total_price = itemView.findViewById(R.id.totalProductOrder);

        }
    }
}
