package com.example.banquanao.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.banquanao.R;
import com.example.banquanao.Fragment.DeliveredFragment;
import com.example.banquanao.Fragment.EmployeeFragment;
import com.example.banquanao.Fragment.OrderFragment;
import com.example.banquanao.Fragment.SuccessFragment;
import com.example.banquanao.database.Customer;
import com.example.banquanao.database.Staff;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class PageStaffActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public String DATABASE_NAME = "databaseApp.db";
    public String DB_SUFFIX_PATH = "/databases/";
    public static SQLiteDatabase database =null;


    //private static final int FRAGMENT_HOME =0;
    private static final int FRAGMENT_EMPLOYEE =0;
    private static final int FRAGMENT_ORDER =1;
    private static final int FRAGMENT_DELI =2;
    private static final int FRAGMENT_SUCCESS =3;


    private int mCurrentFragment = FRAGMENT_ORDER;


    Toolbar toolbarMainpage;
    NavigationView navView;
    DrawerLayout drawerLayout;
    public static Staff staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_staff_main);

        addControl();
        ActionBar();
        //database

        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        loadData();

    }
    private void loadData() {
        //truy vấn database

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            staff = getIntent().getSerializableExtra("Staff", Staff.class);
        }
    }

    public String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_SUFFIX_PATH + DATABASE_NAME;
    }

    private void proccessCopy() {
        try{
            File file = getDatabasePath(DATABASE_NAME);
            if(!file.exists())
                copyDatabaseFromAsset();
            Toast.makeText(this, "Copy Database Successful",Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            Toast.makeText(this, "Copy Database Fail",Toast.LENGTH_SHORT).show();
        }
    }

    private void copyDatabaseFromAsset() {
        try{
            InputStream inputFile = getAssets().open(DATABASE_NAME);
            String outputFileName = getDatabasePath();
            File file = new File(getApplicationInfo().dataDir + DB_SUFFIX_PATH);
            if(!file.exists())
                file.mkdir();
            OutputStream outFile = new FileOutputStream(outputFileName);
            byte [] buffer = new byte[1024];
            int length;
            while((length =inputFile.read(buffer))>0)
                outFile.write(buffer,0,length);
            outFile.flush();
            outFile.close();
            inputFile.close();
        }
        catch(Exception ex){
            Log.e("Error",ex.toString());
        }

    }

    private void ActionBar(){
        setSupportActionBar(toolbarMainpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarMainpage.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarMainpage.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void addControl() {
        toolbarMainpage = findViewById(R.id.toolbarMainpage);
        navView = findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(this);
        replaceFragment(new OrderFragment());
        drawerLayout = findViewById(R.id.drawerLayout);
        staff =new Staff();
        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_order){
            if(mCurrentFragment!=FRAGMENT_ORDER){
                replaceFragment(new OrderFragment());
                mCurrentFragment = FRAGMENT_ORDER;
                toolbarMainpage.setTitle("Trang ");
            }
        }
        else if(id==R.id.nav_infor){
            if(mCurrentFragment!=FRAGMENT_EMPLOYEE){
                replaceFragment(new EmployeeFragment());
                mCurrentFragment = FRAGMENT_EMPLOYEE;
                toolbarMainpage.setTitle("Trang thông tin");
            }
        }
//        else if(id==R.id.nav_home){
//            if(mCurrentFragment!=FRAGMENT_HOME){
//                replaceFragment(new HomeFragment());
//                mCurrentFragment = FRAGMENT_HOME;
//            }
//        }
        else if(id==R.id.nav_deli){
            if(mCurrentFragment!=FRAGMENT_DELI){
                replaceFragment(new DeliveredFragment());
                mCurrentFragment = FRAGMENT_DELI;
                toolbarMainpage.setTitle("Trang giao hàng");
            }
        }
        else if(id==R.id.nav_success){
            if(mCurrentFragment!=FRAGMENT_SUCCESS){
                replaceFragment(new SuccessFragment());
                mCurrentFragment = FRAGMENT_SUCCESS;
                toolbarMainpage.setTitle("Trang giao hàng thành công");
            }
        }else if (id==R.id.nav_logout){
            showConfirmLogOut();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //an back de thoat nav
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }
    public static Bitmap TranslateByteToBitmap(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap;
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
        staff= new Staff();
        Intent intent = new Intent(this,PageMainLoginActivity.class);
        startActivity(intent);
    }


}