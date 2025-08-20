package com.example.banquanao.ChangeInforUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;
import com.example.banquanao.SendOTP.SendOTP;
import com.example.banquanao.database.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class ChangeInforActivity extends AppCompatActivity {

    EditText nameInfor,emailInfor,numberphonrInfor,addressInfor;
    Button btnConfirm;
    public static Customer customer;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_infor);
        setSupportActionBar(findViewById(R.id.toolbarChange));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addControls();
        handleChangeInfor();
    }

    private void handleChangeInfor() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberPhone = numberphonrInfor.getText().toString().trim();
                String email = emailInfor.getText().toString().trim();

                Intent intent = new Intent();
                customer.setName_Cus(nameInfor.getText().toString().trim());
                if(isValidEmail(email)){
                    customer.setEmail(emailInfor.getText().toString().trim());
                }else{
                    PageMainCus.MyToast(getApplicationContext(),"Email không hợp lý !");
                }
                customer.setAddress(addressInfor.getText().toString().trim());
                if(customer.getNumberPhone() != Integer.parseInt(numberPhone)){
                    PageMainCus.customer.setNumberPhone(Integer.parseInt(numberPhone));
                }
                intent.putExtra("User_Changed",customer);
                setResult(RESULT_OK,intent);
                finish();
            }


        });
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,6}$";
        return Pattern.matches(regex, email);
    }
    private void addControls() {
        customer = (Customer) getIntent().getSerializableExtra("user");
        nameInfor = findViewById(R.id.nameInfor);
        emailInfor = findViewById(R.id.emailInfor);
        numberphonrInfor = findViewById(R.id.numberphoneInfor);
        addressInfor = findViewById(R.id.addressInfor);
        btnConfirm =findViewById(R.id.btnConfirm);
        mAuth= FirebaseAuth.getInstance();
        display();
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            String phone = "0"+user.getPhoneNumber().toString().substring(3);
                            customer.setNumberPhone(Integer.parseInt(phone));
                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
    private void gotoSendOTP(String phone, String s) {
        Intent intent = new Intent(this, SendOTP.class);
        intent.putExtra("numberphone",phone);
        intent.putExtra("verificationID",s);
        startActivity(intent);
    }

    private void display(){
       nameInfor.setText(customer.getName_Cus());
       emailInfor.setText(customer.getEmail());
       numberphonrInfor.setText(customer.getNumberPhone()+"");
       addressInfor.setText(customer.getAddress());
    }



}