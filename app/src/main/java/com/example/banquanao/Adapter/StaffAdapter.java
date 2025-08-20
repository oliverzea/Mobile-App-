package com.example.banquanao.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banquanao.R;
import com.example.banquanao.Utils;
import com.example.banquanao.Fragment.StaffFragment;
import com.example.banquanao.database.Staff;

import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHold> {
    StaffFragment context;
    List<Staff> list_Staff;

    public StaffAdapter(StaffFragment context, List<Staff> list_Staff) {
        this.context = context;
        this.list_Staff = list_Staff;
    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context.getContext());
        View viewStaff=layoutInflater.inflate(R.layout.item_staff,parent,false);
        ViewHold viewHolderS=new ViewHold(viewStaff);
        return viewHolderS;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Staff staff=list_Staff.get(position);
        holder.txtNameS.setText(staff.getName());
        holder.txtIdS.setText("ID : "+staff.getId_Staff()+"");
        holder.txtPhone.setText("Phone: "+staff.getNumber_phone()+"");
        holder.txtPass.setText("Pass : "+staff.getPassWork());
        holder.txtEmail.setText("Email: "+staff.getEmail());
        if (staff.getImageStaff() != null) {
            holder.ivImage.setImageBitmap(Utils.TranslateByteToBitmap(staff.getImageStaff()));
        }
    }

    @Override
    public int getItemCount() {
        if(list_Staff.size()<=0){
            return 0;
        }
        return list_Staff.size();
    }
    public class ViewHold extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView txtNameS, txtIdS, txtEmail, txtPhone, txtBirthday, txtPass, txtidMana;


        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.imageS);
            txtNameS = itemView.findViewById(R.id.txtNameS);
            txtIdS = itemView.findViewById(R.id.txtIdS);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtPass = itemView.findViewById(R.id.txtPass);
        }
    }
}
