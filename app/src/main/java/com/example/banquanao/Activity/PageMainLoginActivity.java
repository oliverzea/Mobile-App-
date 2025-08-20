package com.example.banquanao.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banquanao.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class PageMainLoginActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database=null;
    String DATABASE_NAME="databaseApp.db";
    Button btLogin, btRegister, btRegisterStaff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_main_login);
        processCopy();
        database= openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        eventClickOn();

    }

    public String getDatabasePath(){
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;

    }
    private void processCopy() {
        try {
            File file = getDatabasePath(DATABASE_NAME);
            if (!file.exists()) {
                copyDatabaseFromAsset();
                Toast.makeText(this,"Copy Database Successful",Toast.LENGTH_SHORT).show();
            }

        }
        catch(Exception ex) {
            Toast.makeText(this,"Copy Database Fail",Toast.LENGTH_SHORT).show();
        }

    }

    private void copyDatabaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception ex) {
            Log.e("Error",ex.toString());
        }
    }
    private void eventClickOn() {
        // Chuyển đến trang Login
        btLogin = findViewById(R.id.buttonLogin_Main);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PageMainLoginActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        // Chuyển đến trang Register
        btRegister = findViewById(R.id.buttonRegister_Main);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PageMainLoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
// Chuyển đến trang Register Staff
        btRegisterStaff = findViewById(R.id.buttonRegisterStaff_Main);
        btRegisterStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PageMainLoginActivity.this, RegisterStaffActivity.class);
                startActivity(i);
            }
        });
    }
    public static void showConfirmLogOut(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có chắc muốn đăng xuất?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //gotoLoginScreen();
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
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.app_main_bg, null));
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.gray_light, null));
            }
        });
        alert.show();
    }


}