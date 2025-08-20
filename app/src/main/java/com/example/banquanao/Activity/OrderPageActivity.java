package com.example.banquanao.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banquanao.database.Cart;
import com.example.banquanao.ChangeInforUser.ChangeInforActivity;
import com.example.banquanao.Fragment.CartFragment;
import com.example.banquanao.Fragment.HomeFragment;
import com.example.banquanao.R;
import com.example.banquanao.Adapter.OrderRecyclerview;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Order;
import com.example.banquanao.database.OrderDetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderPageActivity extends AppCompatActivity {
    Toolbar toolbar;
    Customer customer_order;
    TextView txtNameCus, txtNumberphoneCus, txtAddress, txtTotalOrder;
    RecyclerView recyclerViewOrder;
    OrderRecyclerview orderRecyclerview;
    Button btnChangeInfor, btnConfirmPay;
    public static final int REQUEST_CODE = 99;
    List<Cart> itemOrder,cartWasPay;
    RadioButton payByCard, payByCash;
    Order tbOrder;
    List<OrderDetail> listDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        Log.e("sa","Create of order page");
        addControls();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataCus(customer_order);
        handleButton();
        handleConfirmPay();


    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataCus(PageMainCus.customer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e("sa","Destroy of order page");
    }

    private void handleButton() {
        btnChangeInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderPageActivity.this, ChangeInforActivity.class);
                intent.putExtra("user", customer_order);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void getDataCus(Customer customer) {
        txtNameCus.setText("Tên khách hàng: " + customer.getName_Cus());
        txtNumberphoneCus.setText("Số điện thạoi: " + customer.getNumberPhone());
        txtAddress.setText("Địa chỉ: " + customer.getEmail());
        txtTotalOrder.setText("Tổng tiền" + getTotalProduct());
    }

    private void getDataItems() {
        Intent intent = getIntent();
        if (intent.hasExtra("cartList")) {
            itemOrder = intent.getParcelableArrayListExtra("cartList");

        } else {
            Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        itemOrder = new ArrayList<>();
        cartWasPay = new ArrayList<>();
        toolbar = findViewById(R.id.toolbarOrder);
        txtNameCus = findViewById(R.id.nameCustomer);
        txtNumberphoneCus = findViewById(R.id.numberPhoneCustomer);
        txtAddress = findViewById(R.id.addressDelivery);
        txtTotalOrder = findViewById(R.id.totalOrder);
        btnChangeInfor = findViewById(R.id.changeInfor);
        btnConfirmPay = findViewById(R.id.btnConfirmPay);
        recyclerViewOrder = findViewById(R.id.recylcerOrder);
        customer_order = (Customer) getIntent().getSerializableExtra("user");
        getDataItems();
        orderRecyclerview = new OrderRecyclerview(itemOrder, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewOrder.setLayoutManager(linearLayoutManager);
        recyclerViewOrder.setAdapter(orderRecyclerview);
       // orderRecyclerview.notifyDataSetChanged();
        payByCard = findViewById(R.id.payByCard);
        payByCash = findViewById(R.id.payByCash);
        tbOrder = new Order();
        listDetail= new ArrayList<>();
    }

    private double getTotalProduct() {
        double total = 0;
        for (int i = 0; i < itemOrder.size(); i++) {
            double total_product = itemOrder.get(i).getQuantity_cart() * itemOrder.get(i).getPrice();
            total += total_product;
        }

        return total;
    }

    private void handleConfirmPay() {
        btnConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payBy = "";
                if (payByCard.isChecked()) {
                    // Pay by card
                    if (customer_order.getId_card() > 0) {
                        payBy = "Thanh toán bằng thẻ";
                    } else {
                        PageMainCus.MyToast(getApplicationContext(), "Vui lòng nhập thẻ !");
                        return;
                    }
                } else if (payByCash.isChecked()) {
                    //pay by cash
                    payBy = "Thanh toán bằng tiền mặt";
                } else {
                    PageMainCus.MyToast(getApplicationContext(), "Bạn cần phải chọn phương thức thanh toán");
                    return;
                }
                AddDataToIOrder(payBy);
                addOrderDetail();
                insertData(listDetail);
                itemOrder=new ArrayList<>();
                orderRecyclerview = new OrderRecyclerview(new ArrayList<>(), getApplicationContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewOrder.setLayoutManager(linearLayoutManager);
                recyclerViewOrder.setAdapter(orderRecyclerview);
                orderRecyclerview.notifyDataSetChanged();
                clearCart(itemOrder);
                getSharedPreferences(CartFragment.nameShared,MODE_PRIVATE).edit().clear().apply();
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("listPay", (ArrayList<? extends Parcelable>) itemOrder);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    public  void clearCart(List<Cart> itemOrder){
        CartFragment.sendCart.removeAll(itemOrder);
        CartFragment.listView.setAdapter(CartFragment.cartAdapter);
        CartFragment.cartAdapter.notifyDataSetChanged();
        Log.e("s","SAu khi mua"+CartFragment.sendCart.size()+"");
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.hasExtra("User_Changed")) {
                customer_order = (Customer) data.getSerializableExtra("User_Changed");
                getDataCus(customer_order);
                PageMainCus.setUser(customer_order);
                PageMainCus.updateUser(customer_order);
            } else {
                PageMainCus.MyToast(getApplicationContext(), "Thay đổi thông tin thất bại");
            }
        }
    }

    private int getIdMax() {
        String query = "select max(id_Order) from Orders";
        Cursor c = PageMainCus.database.rawQuery(query,new String[]{});
        c.moveToFirst();
        return c.getInt(0);
    }
    private int getIdDetailMax() {
        String query = "select max(id_detail) from Order_Detail";
        Cursor c = PageMainCus.database.rawQuery(query,new String[]{});
        c.moveToFirst();
        return c.getInt(0);
    }
    private OrderDetail AddDataDetail(Cart cart,int k){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId_order(tbOrder.getId_Order());
        orderDetail.setId_detail(getIdDetailMax()+k);
        orderDetail.setId_Product(cart.getId());
        orderDetail.setPrice(cart.getPrice());
        orderDetail.setNumber(cart.getQuantity_cart());
        orderDetail.setPrice_total(orderDetail.getPrice()*orderDetail.getNumber());
        return orderDetail;
    }

    private void addOrderDetail(){
        for(int i=0;i<itemOrder.size();i++){
            listDetail.add(AddDataDetail(itemOrder.get(i),i+1));
        }
    }

    private void AddDataToIOrder(String payBy){
        tbOrder.setId_Order(getIdMax()+1);
        tbOrder.setId_Cus(customer_order.getId_cus());
        tbOrder.setToPay(payBy);
        tbOrder.setId_State(1);
        String dateOrder;
        Calendar r = Calendar.getInstance();
        dateOrder = r.get(Calendar.YEAR)+"/"+r.get(Calendar.MONTH)+"/"+r.get(Calendar.DATE);
        tbOrder.setOrderDate(dateOrder);
        tbOrder.setPrice_total(getTotalProduct());
    }

    private void insertData(List<OrderDetail> mlist){
        insertDataOrder();
        for(int i=0;i<mlist.size();i++){
            inserDataDetail(mlist.get(i));
        }

    }

    private void insertDataOrder() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_Order",tbOrder.getId_Order());
        contentValues.put("id_Cus",tbOrder.getId_Cus());
        contentValues.put("id_Manager",tbOrder.getId_Manager());
        contentValues.put("id_Staff",tbOrder.getId_Staff());
        contentValues.put("price_total",tbOrder.getPrice_total());
        contentValues.put("id_State",tbOrder.getId_State());
        contentValues.put("toPay",tbOrder.getToPay());
        contentValues.put("orderDate",tbOrder.getOrderDate());
        contentValues.put("expertedDate",tbOrder.getExpertedDate());

        long rowId =  PageMainCus.database.insert(PageMainCus.table_order,null,contentValues);

//        if(rowId!=-1){
//            MainActivity.MyToast(getApplicationContext(),"Chen thanh cong "+ rowId);
//        }else{
//            MainActivity.MyToast(getApplicationContext(),"Chen khong thanh cong");
//        }
    }

    private void inserDataDetail(OrderDetail orderDetail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_detail",orderDetail.getId_detail());
        contentValues.put("id_order",orderDetail.getId_order());
        contentValues.put("id_Product",orderDetail.getId_Product());
        contentValues.put("price",orderDetail.getPrice());
        contentValues.put("number",orderDetail.getNumber());
        contentValues.put("price_total",orderDetail.getPrice_total());
        contentValues.put("id_Size",orderDetail.getId_Size());

        long rowId = PageMainCus.database.insert(PageMainCus.table_orderDetail,null,contentValues);
//        if(rowId!=-1){
//            MainActivity.MyToast(getApplicationContext(),"Chen thanh cong "+ rowId);
//        }else{
//            MainActivity.MyToast(getApplicationContext(),"Chen khong thanh cong");
//        }
        for(int i=0;i<HomeFragment.mlistProduct.size();i++){
            if(HomeFragment.mlistProduct.get(i).getId()==orderDetail.getId_Product()){
                int new_quantity = HomeFragment.mlistProduct.get(i).getQuantity()-orderDetail.getNumber();
                int id = orderDetail.getId_Product();
                HomeFragment.mlistProduct.get(i).setQuantity(new_quantity);
                HomeFragment.quantityOfProduct[HomeFragment.mlistProduct.get(i).getId()] = new_quantity;
                PageMainCus.MyToast(getApplicationContext(),"So luong con lai: "+HomeFragment.quantityOfProduct[HomeFragment.mlistProduct.get(i).getId()]);

                ContentValues pro = new ContentValues();
                pro.put("quantity",new_quantity);

                long rowIdPro = PageMainCus.database.update(PageMainCus.table_product,pro,"id_product=?",new String[]{String.valueOf(id)});

//            if(rowIdPro!=-1){
//                MainActivity.MyToast(getApplicationContext(),"Chen thanh cong "+ rowId);
//            }else{
//                MainActivity.MyToast(getApplicationContext(),"Chen khong thanh cong");
//            }
            }
        }
    }
}