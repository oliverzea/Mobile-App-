package com.example.banquanao.ChangeInforUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;
import com.example.banquanao.SendOTP.SendOTP;
import com.example.banquanao.database.Customer;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class ChangeInformationUser extends AppCompatActivity {

    EditText editName,editEmail,editNumberPhone,editAddress;
    Button  btnChangePass,btnChangeCard,btnConfirm;
    public static final int REQUESCODE_IMAGE=987;
    ImageView imgCustomer;
    FirebaseAuth mAuth;
    public static Customer customer = new Customer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information_user);
        addControls();
        addButton();
        addText();

    }

    private void addButton() {
        btnChangeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeInformationUser.this,ChangeCard.class);
                startActivity(intent);
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeInformationUser.this,ChangePass.class);
                startActivity(intent);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            String name , email, address,numberPhone;
            int number;
            @Override
            public void onClick(View v) {

                name =editName.getText().toString().trim();
                email =editEmail.getText().toString().trim();


                address =editAddress.getText().toString().trim();
                number = Integer.parseInt(editNumberPhone.getText().toString().trim());

                if(editNumberPhone.getText().toString().trim()!=null){
                    numberPhone=editNumberPhone.getText().toString().trim();

                }else{
                    numberPhone="";
                }

                customer.setName_Cus(name);
                if(isValidEmail(email)){
                    customer.setEmail(email);
                }else {
                    PageMainCus.MyToast(getApplicationContext(),"Email không hợp lệ");
                }

                customer.setAddress(address);
                customer.setImgCus(PageMainCus.TranslateBitmapToByeImage(imgCustomer));
                if(PageMainCus.customer.getNumberPhone()!=number && numberPhone!=null){
                   customer.setNumberPhone(number);
                }

                Intent intent= new Intent();
                intent.putExtra("customer",customer);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        imgCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUESCODE_IMAGE);
            }
        });
    }


    private void addText() {
        if(PageMainCus.customer==null){
            Log.e("test","Customer is null");
            return;
        }
        editName.setText(PageMainCus.customer.getName_Cus());
        editEmail.setText(PageMainCus.customer.getEmail());
        editAddress.setText(PageMainCus.customer.getAddress());
        editNumberPhone.setText(PageMainCus.customer.getNumberPhone()+"");
        imgCustomer.setImageBitmap(PageMainCus.TranslateByteToBitmap(PageMainCus.customer.getImgCus()));
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,6}$";
        return Pattern.matches(regex, email);
    }
    private void addControls() {
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editNumberPhone = findViewById(R.id.editNumberPhone);
        editAddress = findViewById(R.id.editAddress);
        btnChangePass = findViewById(R.id.changePass);
        btnChangeCard = findViewById(R.id.changeCard);
        btnConfirm = findViewById(R.id.confirm);
        imgCustomer= findViewById(R.id.imgCustomer);
        mAuth=FirebaseAuth.getInstance();
        setSupportActionBar(findViewById(R.id.toolbarChangeInfor));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void verificationNumberphone(String numberPhone) {
        String phone = "+84"+numberPhone.substring(1);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                editNumberPhone.setText(PageMainCus.customer.getNumberPhone()+"");
                                PageMainCus.MyToast(getApplicationContext(),"Number phone verification failed");
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                gotoSendOTP(phone,s);
                            }


                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void gotoSendOTP(String phone, String s) {
        Intent intent = new Intent(this, SendOTP.class);
        intent.putExtra("numberphone",phone);
        intent.putExtra("verificationID",s);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESCODE_IMAGE&&resultCode==RESULT_OK&&data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgCustomer.setImageBitmap(bitmap);
        }
    }
}