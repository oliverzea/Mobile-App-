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
import com.example.banquanao.database.Size;

import java.util.List;

public class SizeAdapter  extends RecyclerView.Adapter<SizeAdapter.ViewHoldSize>{
    Context context;
   List<Size> lst_Size;
   SizeOnClickListener sizeOnClickListener;


    public SizeAdapter(Context context, List<Size> lst_Size, SizeOnClickListener sizeOnClickListener) {
        this.context = context;
        this.lst_Size = lst_Size;
        this.sizeOnClickListener = sizeOnClickListener;
    }

    @NonNull
    @Override
    public ViewHoldSize onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View viewSize=layoutInflater.inflate(R.layout.item_size,parent,false);
        ViewHoldSize viewHolderSize=new ViewHoldSize(viewSize);
        return viewHolderSize;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldSize holder, int position) {
        Size size = lst_Size.get(position);
        holder.txtIdSize.setText(size.getIdSize()+"");
        holder.txtHeight.setText(size.getHeight()+"");
        holder.txtWeight.setText(size.getWeight()+"");
        holder.txtSex.setText(size.getSex()+"");
        holder.txtType.setText(size.getType()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sizeOnClickListener!=null){
                    sizeOnClickListener.didSelectAtSize(holder.getAdapterPosition());
                }
            }
        });
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, SizeDetailActivity.class);
//                intent.putExtra("SizeDetail",size);
//                context.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        if(lst_Size.size()<=0){
            return 0;
        }
        return lst_Size.size();
    }
    public class ViewHoldSize extends RecyclerView.ViewHolder {
        TextView txtIdSize,txtHeight,txtWeight,txtSex,txtType;
        LinearLayout linearLayout;
        public ViewHoldSize(@NonNull View itemViewSize) {
            super(itemViewSize);
            txtIdSize = itemViewSize.findViewById(R.id.txtIdSize);
            txtHeight = itemViewSize.findViewById(R.id.txtHeight);
            txtWeight = itemViewSize.findViewById(R.id.txtWeight);
            txtSex = itemViewSize.findViewById(R.id.txtSex);
            txtType = itemViewSize.findViewById(R.id.txtType);
            linearLayout=itemView.findViewById(R.id.itemSize);
        }
    }

    public interface SizeOnClickListener{
           void didSelectAtSize(int position);
    }


}
