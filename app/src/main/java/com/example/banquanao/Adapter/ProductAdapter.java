package com.example.banquanao.Adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Utils;
import com.example.banquanao.Activity.ProductDetailActivity;
import com.example.banquanao.Fragment.ProductFragment;
import com.example.banquanao.database.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHold>{

    ProductFragment context;
    List<Product> list_Product;

    public ProductAdapter(ProductFragment context, ArrayList<Product> list_Product){
        this.context=context;
        this.list_Product=list_Product;

    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context.getContext());
        View viewProduct=layoutInflater.inflate(R.layout.item_product,parent,false);
        ViewHold viewHolderPro=new ViewHold(viewProduct);
        return viewHolderPro;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Product product=list_Product.get(position);
        holder.txtNamePro.setText(product.getName());
        holder.txtIdPro.setText(product.getId()+"");
        holder.txtPrice.setText(product.getPrice()+"");
        holder.txtSize.setText(product.getSize()+"");
        holder.txtCate.setText(product.getId_category()+"");
        //chuyen byte -> bitmap

     //   byte[] bytes = product.getImagePro();

       // Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        if (product.getIdResourse() != null) {
            holder.ivImage.setImageBitmap(Utils.TranslateByteToBitmap(product.getIdResourse()));
        }

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chuyen trang
                Intent intent = new Intent(context.getContext(), ProductDetailActivity.class);
                //gui thong tin san pham Product
                intent.putExtra("Product_Fragment",product);
                context.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        if(list_Product.size()<=0){
            return 0;
        }
        return list_Product.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView txtNamePro, txtIdPro, txtPrice, txtSize, txtCate;
        LinearLayout click ;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.imagePro);
            txtNamePro = itemView.findViewById(R.id.txtNamePro);
            txtIdPro = itemView.findViewById(R.id.txtIdPro);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtCate = itemView.findViewById(R.id.txtCate);
            click = itemView.findViewById(R.id.moveDetailP);
        }
    }
}
