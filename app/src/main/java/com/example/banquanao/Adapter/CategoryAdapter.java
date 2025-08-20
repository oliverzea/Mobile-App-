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
import com.example.banquanao.database.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHold> {
    Context context;
    List<Category> list_Cate;

    CategoryOnClickListener onClickListener;

    public CategoryAdapter(Context context, List<Category> list_Cate) {
        this.context = context;
        this.list_Cate = list_Cate;
    }

    public CategoryAdapter(Context context, List<Category> list_Cate, CategoryOnClickListener onClickListener) {
        this.context = context;
        this.list_Cate = list_Cate;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View viewCate=layoutInflater.inflate(R.layout.item_cate,parent,false);
        ViewHold viewHolderCate=new ViewHold(viewCate);
        return viewHolderCate;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Category category=list_Cate.get(position);
        holder.txtIdCate.setText(category.getId()+"");
        holder.txtNameCate.setText(category.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener!=null){
                    onClickListener.didSelectAt(holder.getAdapterPosition());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if(list_Cate.size()<=0){
            return 0;
        }
        return list_Cate.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        TextView txtNameCate,txtIdCate;
        LinearLayout linearLayout;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            txtNameCate = itemView.findViewById(R.id.txtNameCate);
            txtIdCate = itemView.findViewById(R.id.txtIdCate);
            linearLayout=itemView.findViewById(R.id.itemCategory);
        }
    }

    public interface CategoryOnClickListener{
        void didSelectAt(int position);
    }
}
