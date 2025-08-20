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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.banquanao.R;
import com.example.banquanao.Fragment.CategoryFragment;
import com.example.banquanao.Fragment.ProductFragment;
import com.example.banquanao.Fragment.StaffFragment;
import com.example.banquanao.Fragment.UserFragment;
import com.example.banquanao.database.Product;
import com.example.banquanao.database.Staff;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class HomeActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    //public static SQLiteDatabase database=null;
    String DATABASE_NAME="databaseApp.db";
    Toolbar myToolbar;
    DrawerLayout myDrawer;
    NavigationView myNavigation;
    View drawerHeader;
    ImageButton btAvatar,btSetting;
    Button btnHeader;
    public static  Staff staff;
    ImageButton imageButtonAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        //Gọi hàm Copy CSDL từ assets vào thư mục Databases
//        processCopy();
//        //Mở CSDL lên để dùng
//        database = openOrCreateDatabase("databaseApp.db",MODE_PRIVATE, null);
        myToolbar = findViewById(R.id.materialToolbarHome);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("Category");
        myDrawer=findViewById(R.id.drawerLayoutHome);
        myNavigation=findViewById(R.id.NavigationViewHome);
        myNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int size=myNavigation.getMenu().size();
                for(int i =0;i<size;i++) {
                    myNavigation.getMenu().getItem(i).setChecked(false);
                }
                if(item.getItemId()==R.id.mnDrawerItemSize){
                    Intent i = new Intent(HomeActivity.this,SizeActivity.class);
                    startActivity(i);

                }
                if(item.getItemId()==R.id.myDrawerLogout){

                    showConfirmLogOut();
                }
                item.setChecked(true);
                myDrawer.close();
                return true;
            }
        });
        drawerHeader =myNavigation.getHeaderView(0);
        btnHeader= myNavigation.getHeaderView(0).findViewById(R.id.buttonNameHeader);
        imageButtonAcc=drawerHeader.findViewById(R.id.imageButtonAvatar);
        imageButtonAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (staff != null) {
                    Intent i = new Intent(HomeActivity.this, AccountActivity.class);
                    i.putExtra("AccountStaffMana",staff);
                    startActivity(i);
                }
            }
        });
        btSetting=drawerHeader.findViewById(R.id.imageButtonSetting);
        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (staff != null) {
                    Intent i = new Intent(HomeActivity.this, AccountActivity.class);
                    i.putExtra("AccountStaffMana",staff);
                    startActivity(i);
                }
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewHome);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener());
        setFragment(new CategoryFragment());



    }

    @Override
    protected void onStart() {
        super.onStart();
        getInforManager();
        btnHeader.setText(staff.getName());
        if(staff.getImageStaff()!=null){
            imageButtonAcc.setImageBitmap(HomeActivity.TranslateByteToBitmap(staff.getImageStaff()));
        }
    }

    private void getInforManager() {
        Intent intent = getIntent();
        staff = new Staff();
        if(intent.hasExtra("Manager")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                staff = intent.getSerializableExtra("Manager",Staff.class);
            }
        }

    }

    void setFragment(Fragment newFragment) {
        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragmentContainerView, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            myDrawer.open();
        }
        if (item.getItemId() == R.id.mnItemState) {
            Intent i = new Intent(HomeActivity.this, StateActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private NavigationBarView.OnItemSelectedListener onItemSelectedListener() {
        return new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.bottomItemCategory) {
                    setFragment(new CategoryFragment());
                    myToolbar.setTitle("Category");
                }
                if(item.getItemId() == R.id.bottomItemProduct) {
                    setFragment(new ProductFragment());
                    myToolbar.setTitle("Product");
                }
                if(item.getItemId() == R.id.bottomItemStaff) {
                    setFragment(new StaffFragment());
                    myToolbar.setTitle("Staff");
                }
                if(item.getItemId() == R.id.bottomItemUser) {
                    setFragment(new UserFragment());
                    myToolbar.setTitle("User");
                }
                return true;
            }
        };
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