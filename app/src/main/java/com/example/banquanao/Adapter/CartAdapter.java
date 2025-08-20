package com.example.banquanao.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banquanao.database.Cart;
import com.example.banquanao.Fragment.CartFragment;
import com.example.banquanao.Fragment.HomeFragment;
import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    List<Cart> carts;
    Context context;


    public static int[] quantityOfCart;
    public CartAdapter(List<Cart> carts, Context context) {
        this.carts = carts;
        this.context = context;


        quantityOfCart = new int[carts.size()];
        for(int i=0;i<carts.size();i++){
            quantityOfCart[i]=carts.get(i).getQuantity_cart();
        }
    }

    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int position) {
        return carts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return carts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        TextView txtName, txtPrice,txtPriceCart;
        ImageView imgView;
        EditText  editQuantity;
        Button btnSumCart,btnSubCart,btnRemove;
        LinearLayout linearLayout = convertView.findViewById(R.id.linearClick);
        CheckBox checkBox ;

        checkBox = convertView.findViewById(R.id.checkbox);
        txtName = convertView.findViewById(R.id.txtNameProduct);
        txtPrice = convertView.findViewById(R.id.txtPriceProduct);
        editQuantity = convertView.findViewById(R.id.editQuantityCart);
        imgView=convertView.findViewById(R.id.imgCart);
        btnSumCart=convertView.findViewById(R.id.sumQuantity);
        btnSubCart=convertView.findViewById(R.id.subQuantity);
        txtPriceCart=convertView.findViewById(R.id.txtPriceItemCart);
        btnRemove=convertView.findViewById(R.id.btnRemove);


        Cart cart = carts.get(position);
        checkBox.setChecked(PageMainCus.check_click[cart.getId()]);

     //   cart.setCheck(checkBox.isChecked());
        Bitmap bitmap = PageMainCus.TranslateByteToBitmap(cart.getImg());
        txtName.setText("Tên sản phẩm: "+ cart.getName());
        txtPrice.setText("Giá sản phẩm: "+ cart.getPrice());
        editQuantity.setText(cart.getQuantity_cart()+"");
        txtPriceCart.setText(cart.getTotal()+"");
        imgView.setImageBitmap(bitmap);


        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()==true){
                    CartFragment.mlistCart.removeIf(cart1 -> cart1.getId()==cart.getId());
                    CartFragment.sendCart.removeIf(cart1 -> cart1.getId()==cart.getId());
                    PageMainCus.total_backup= PageMainCus.total_backup-cart.getTotal();
                    PageMainCus.utils.get(cart.getId()).setQuantity(0);
                    CartFragment.total_price-=cart.getTotal();
                    CartFragment.total.setText(CartFragment.total_price+"");
                    notifyDataSetChanged();
                }else{
                    CartFragment.mlistCart.removeIf(cart1 -> cart1.getId()==cart.getId());
                    CartFragment.sendCart.removeIf(cart1 -> cart1.getId()==cart.getId());
                    notifyDataSetChanged();
                }

            }
        });
        btnSumCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    int i = Integer.parseInt(editQuantity.getText().toString().trim());
                    double price =0;
                    price= Double.parseDouble(CartFragment.total.getText().toString().trim());
                    price =price-cart.getTotal();
                    if(i+1<= HomeFragment.quantityOfProduct[cart.getId()]){
                        cart.setQuantity_cart(i+1);
                        cart.setTotal(cart.getQuantity_cart()*cart.getPrice());
                        price+=cart.getTotal();
                        //Toast.makeText(context,"So luong hien tai: "+i,Toast.LENGTH_SHORT).show();
                        editQuantity.setText(cart.getQuantity_cart()+"");
                        txtPriceCart.setText(cart.getTotal()+"");
                        PageMainCus.utils.get(position).setQuantity(cart.getQuantity_cart());
                        CartFragment.total_price=price;
                        PageMainCus.total_backup=price;
                        CartFragment.total.setText(price+" ");
                    }else{
                        PageMainCus.MyToast(context,"Hàng không đủ");
                    }

                }else {
                    PageMainCus.MyToast(context,"Bạn nên tích vào nút giỏ hàng");
               }

            }
        });

        btnSubCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    int i = Integer.parseInt(editQuantity.getText().toString().trim());
                    if(i>0){
                        double price =0;
                        price= Double.parseDouble(CartFragment.total.getText().toString().trim());
                        price =price-cart.getTotal();
                        cart.setQuantity_cart(i-1);
                        cart.setTotal(cart.getQuantity_cart()*cart.getPrice());
                        price+=cart.getTotal();
                        //Toast.makeText(context,"So luong hien tai: "+i,Toast.LENGTH_SHORT).show();
                        editQuantity.setText(cart.getQuantity_cart()+"");
                        txtPriceCart.setText(cart.getTotal()+"");
                        PageMainCus.utils.get(position).setQuantity(cart.getQuantity_cart());
                        PageMainCus.total_backup=price;
                        CartFragment.total_price=price;
                        CartFragment.total.setText(price+" ");
                    }else{
                        Toast.makeText(context,"Số lượng không hợp lệ",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    PageMainCus.MyToast(context,"Bạn nên tích vào nút giỏ hàng");

                }

            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
                PageMainCus.check_click[cart.getId()]=checkBox.isChecked();
             //   MainActivity.check_click.get(position).setCheck(checkBox.isChecked());
                if(checkBox.isChecked()==false){
                    if(CartFragment.sendCart.contains(cart)==true){
                        CartFragment.sendCart.remove(cart);
                    }
                    double totalCart = cart.getTotal();
                    PageMainCus.total_backup=CartFragment.total_price;
                    CartFragment.total_price -=totalCart;
                    CartFragment.total.setText(CartFragment.total_price+"");


                }else{
                    if( CartFragment.sendCart.contains(cart)==false){
                        CartFragment.sendCart.add(cart);
                    }
                    double totalCart = cart.getTotal();
                    CartFragment.total_price +=totalCart;
                    CartFragment.total.setText(CartFragment.total_price+"");


                }


            }
        });
        return convertView;
    }
}
