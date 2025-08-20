package com.example.banquanao.Adapter;

import static com.example.banquanao.Activity.PageMainCus.Request_code;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.DetailMain.DetailProduct;
import com.example.banquanao.R;
import com.example.banquanao.database.Product;

import java.util.ArrayList;
import java.util.List;

public class PageMainAdapter extends RecyclerView.Adapter<PageMainAdapter.ProductAdapter> implements Filterable {

    List<Product> list;
    List<Product> list_2;
    Context mcontext;
    public PageMainAdapter(List<Product> list,Context context) {
        this.list = list;
        list_2=list;
        mcontext = context;
    }

    @NonNull
    @Override
    public ProductAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page_main,parent,false);
        return new ProductAdapter(view );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter holder, int position) {
        Product product = list.get(position);
        holder.name.setText("Tên sản phẩm: "+product.getName());
        holder.price.setText("Giá tiền: "+product.getPrice());
        Bitmap bitmap= PageMainCus.TranslateByteToBitmap(product.getIdResourse());
        holder.imageView.setImageBitmap(bitmap);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(mcontext, DetailProduct.class);
                intent.putExtra("Product",product);
                ((Activity) mcontext).startActivityForResult(intent,Request_code);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchValues = constraint.toString();

                if(searchValues.isEmpty()){
                    list=list_2;
                }else {
                    List<Product > listPro=new ArrayList<>();
                    for(Product product : list){
                        if(product.getName().toLowerCase().contains(searchValues.toLowerCase())){
                            listPro.add(product);
                        }
                    }

                    list=listPro;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ProductAdapter extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,price;
        LinearLayout linearLayout;
        public ProductAdapter(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearPageMain);
            imageView=itemView.findViewById(R.id.imgPageMain);
            name=itemView.findViewById(R.id.txtName);
            price =itemView.findViewById(R.id.textPrice);
        }
    }
}
