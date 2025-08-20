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
import com.example.banquanao.R;
import com.example.banquanao.database.Bill;

import java.util.List;

public class BillRecyclerView extends RecyclerView.Adapter<BillRecyclerView.BillHolder>{
    private static final int waiting =1;
    private static final int comfim =2;
    private static final int delivery =3;
    private static final int success =4;
    List<Bill> mlist;
    Context mcontext;

    public BillRecyclerView(List<Bill> mlist,Context context) {
        this.mlist = mlist;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill,parent,false);
        return new BillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, int position) {
        Bill bill = mlist.get(position);

        holder.txtNamePro.setText("Tên: "+bill.getName());
        holder.txtPricePro.setText("Giá: "+bill.getPrice()+"");
        holder.txtTotalPro.setText("Tổng: "+bill.getTotal()+"");
        holder.txtQuantityPro.setText("Số lượng: "+bill.getQuantity()+"");
        holder.txtOrder.setText("Đơn hàng thứ: "+bill.getId());
        int idState = bill.getState();
        if(idState==waiting){
            holder.txtStatePro.setText("Đang chờ xác nhận");
            holder.iconState.setImageResource(R.drawable.waiticon);
        }else if(idState==comfim){
            holder.txtStatePro.setText("Đã xác nhận");
            holder.iconState.setImageResource(R.drawable.accepticon);
        }else if(idState==delivery){
            holder.txtStatePro.setText("Đang giao hàng");
            holder.iconState.setImageResource(R.drawable.deliveryicon);
        }else if(idState==success){
            holder.txtStatePro.setText("Đã giao thành công");
            holder.iconState.setImageResource(R.drawable.successicon);
        }
        Bitmap bitmap = PageMainCus.TranslateByteToBitmap(bill.getImg());
        holder.imgPro.setImageBitmap(bitmap);


    }

    @Override
    public int getItemCount() {
        if(mlist!= null){
            return mlist.size();
        }
        return 0;
    }

    public class BillHolder extends RecyclerView.ViewHolder {

        TextView txtNamePro, txtPricePro, txtTotalPro, txtQuantityPro, txtStatePro,txtOrder;
        ImageView imgPro, iconState;

        public BillHolder(@NonNull View itemView) {
            super(itemView);
            txtOrder = itemView.findViewById(R.id.txtOrder);
            txtNamePro = itemView.findViewById(R.id.txtNameProductBill);
            txtPricePro = itemView.findViewById(R.id.txtPriceProductBill);
            txtTotalPro = itemView.findViewById(R.id.txtTotalBill);
            txtQuantityPro = itemView.findViewById(R.id.txtQuantityProductBill);
            txtStatePro = itemView.findViewById(R.id.txtState);
            imgPro = itemView.findViewById(R.id.imgBill);
            iconState = itemView.findViewById(R.id.iconState);
        }
    }

}
