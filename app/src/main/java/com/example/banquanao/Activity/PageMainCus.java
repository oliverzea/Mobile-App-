package com.example.banquanao.Activity;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banquanao.Adapter.FragmentAdapter;
import com.example.banquanao.database.Cart;
import com.example.banquanao.Fragment.CartFragment;
import com.example.banquanao.Fragment.HomeFragment;
import com.example.banquanao.R;
import com.example.banquanao.database.Card;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Product;
import com.example.banquanao.database.Staff;
import com.example.banquanao.database.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PageMainCus extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView ;
    FragmentAdapter fragmentAdapter ;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    public static RecyclerView recyclerView;
    Toolbar toolbar;
    ViewPager viewPager;
    String DATA_PATH = "/databases/";
    public static  SQLiteDatabase database = null;
    String data_name="databaseApp.db";
    public static String table_user = "Customer";
    public static String table_order="Orders";
    public static String table_orderDetail="Order_Detail";
    public static String table_product="Product";
    public static final String table_card="Cart";
    public static Customer customer ;
    public static Card card;
    public static List<Util> utils;
    public static List<Cart> backup;
    public static List<Product> backup_product;
    public static double total_backup=0;
    View header;
    public static TextView useName,numberUser,emailUser;
    public static ImageView imgUser;
    public static final int Request_code = 99;
    int id_Categoru_Ao=2;
    int id_Categoru_Dam=1;
    int id_Categoru_Quan=3;
    int id_Infor =4;
    int current =0;
    public static boolean[] check_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_main_cus);

        addControl();
        database =openOrCreateDatabase(data_name,MODE_PRIVATE,null);
        getCustomer();
        getCard();
        clickItemBottomNavView();
        handelViewPagerFragment();
        handleNavigationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_sreach,menu);
        handleSearch(menu);
        return true;
    }

    private void handelViewPagerFragment(){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.item_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.item_cart).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.item_bill).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.item_user).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addControl(){
        bottomNavigationView = findViewById(R.id.bottom_nav);
        drawerLayout = findViewById(R.id.drawerLayout);
        viewPager = findViewById(R.id.testView);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(fragmentAdapter);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        useName=header.findViewById(R.id.userName);
        numberUser = header.findViewById(R.id.numberPhone);
        emailUser=header.findViewById(R.id.Email);
        imgUser = header.findViewById(R.id.imgUser);
        toolbar = findViewById(R.id.toolbar);
        card=new Card();
        utils = new ArrayList<>();

    }
    private void handleNavigationView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void clickItemBottomNavView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.item_home){
                    toolbar.setTitle("Trang chủ");
                    viewPager.setCurrentItem(0);
                    Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                }else if(id==R.id.item_bill){
                    toolbar.setTitle("Theo dõi đơn hàng");
                    viewPager.setCurrentItem(2);
                    Toast.makeText(getApplicationContext(),"Bill",Toast.LENGTH_SHORT).show();
                }else if(id==R.id.item_cart){
                    toolbar.setTitle("Trang giỏ hàng");
                    viewPager.setCurrentItem(1);
//                    Intent intent = new Intent(MainActivity.this, Bill.class);
//                    startActivity(intent);
                }else if(id==R.id.item_user){
                    toolbar.setTitle("Thông tin cá nhân");
                    viewPager.setCurrentItem(3);
                    Toast.makeText(getApplicationContext(),"User",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }

    private void handleSearch(Menu menu){
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        HomeFragment.searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        HomeFragment.searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        HomeFragment.searchView.setMaxWidth(Integer.MAX_VALUE);


        HomeFragment.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               HomeFragment.adapterPageMain.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               HomeFragment.adapterPageMain.getFilter().filter(newText);
               return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int i = item.getItemId();
        if(i==R.id.cateAo){
           if(current!=id_Categoru_Ao){
               HomeFragment.getProductFormCategory(id_Categoru_Ao);
               current=id_Categoru_Ao;

           }else{
               HomeFragment.getProductBackup(backup_product);
               drawerLayout.closeDrawer(GravityCompat.START);
           }

        }else  if(i==R.id.cateDam){
            if(current!=id_Categoru_Dam){
                HomeFragment.getProductFormCategory(id_Categoru_Dam);
                current=id_Categoru_Dam;

            }else{
                HomeFragment.getProductBackup(backup_product);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }else  if(i==R.id.cateQuan){
            if(current!=id_Categoru_Quan){
                HomeFragment.getProductFormCategory(id_Categoru_Quan);
                current=id_Categoru_Quan;

            }else{
                HomeFragment.getProductBackup(backup_product);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }else  if(i==R.id.logOut){
            showConfirmLogOut();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public static void MyToast(Context context,String data){
        Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
    }
    public void getCustomer(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            customer = getIntent().getSerializableExtra("Customer",Customer.class);
            setUser(customer);
            MyToast(getApplicationContext(),customer.getId_cus()+" id card"+customer.getId_card());
        }

    }
    public void getCard(){
        String query ="select * from Cart where id_Cart=?";
        Cursor c = database.rawQuery(query,new String[]{"1"});
        c.moveToFirst();
        if(c.moveToFirst()){
            card.setId_Card(c.getInt(0));
            card.setNumber_Cart(c.getInt(1));
            card.setDate_issue(c.getString(2));
            card.setNumberCVV(c.getInt(3));
        }else{
            MyToast(getApplicationContext(),"KO LAY DC");
        }

    }

    public static void updateUser(Customer mcustomer){
        ContentValues values = new ContentValues();
        values.put("name",mcustomer.getName_Cus());
        values.put("email",mcustomer.getEmail());
        values.put("number_phone",mcustomer.getNumberPhone());
        values.put("address",mcustomer.getAddress());
        values.put("Image_Customer",mcustomer.getImgCus());
        database.update(table_user,values,"id_Customer=?",new String[]{String.valueOf(customer.getId_cus())});
    }
    public static void setUser(Customer mcustomer){
        useName.setText("Tên khách hàng: "+mcustomer.getName_Cus());
        numberUser.setText("Số điện thoại: "+mcustomer.getNumberPhone());
        emailUser.setText("Tên khách hàng: "+mcustomer.getEmail());
        if(mcustomer.getImgCus()!=null){
            imgUser.setImageBitmap(TranslateByteToBitmap(mcustomer.getImgCus()));
        }
        customer.setName_Cus(mcustomer.getName_Cus());
        customer.setNumberPhone(mcustomer.getNumberPhone());
        customer.setEmail(mcustomer.getEmail());
        customer.setAddress(mcustomer.getAddress());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Request_code&&resultCode==RESULT_OK){
            List<Fragment > fragments= getSupportFragmentManager().getFragments();
            for(Fragment fragment: fragments){
                if(fragment instanceof CartFragment){
                    Cart cart = (Cart) data.getParcelableExtra("cart");
                    CartFragment cartFragment = (CartFragment) fragment;
                    cartFragment.getData(cart);
                }
            }

        }
    }

    public static Bitmap TranslateByteToBitmap(byte[] img){
        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        return bitmap;
    }

    public static byte[] TranslateBitmapToByeImage(ImageView img){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[] bytes = outputStream.toByteArray();
        return bytes;
    }

    public static byte[] TranslateBitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[] bytes = outputStream.toByteArray();
        return bytes;
    }

    private void showConfirmLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc muốn đăng xuất?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        gotoLoginScreen();
                        finish();
                    }
                });

        builder.setNegativeButton(
                "Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        //2. now setup to change color of the button
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.app_main_bg, null));
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.gray_light, null));
            }
        });
        alert.show();
    }
    private void  gotoLoginScreen(){
        customer= new Customer();
        Intent intent = new Intent(this,PageMainLoginActivity.class);
        startActivity(intent);
    }

}