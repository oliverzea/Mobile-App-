package com.example.banquanao.SendOTP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;
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

public class SendOTP extends AppCompatActivity {
    EditText numberOTP;
    Button btnComfirmOTP;
    TextView sendOTPAgain;
    FirebaseAuth mAuth;
    String numberPhone , verificationID;
    PhoneAuthProvider.ForceResendingToken mForceResendingToke;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        addcontrols();
        getData();
        onClickBtn();
    }

    private void onClickBtn() {
        btnComfirmOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpnum= numberOTP.getText().toString().trim();
                PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(verificationID,otpnum);
                signInWithPhoneAuthCredential(authCredential);
            }
        });
        sendOTPAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAgain();
            }


        });
    }

    private void getData() {
        Intent intent = getIntent();
        numberPhone = intent.getStringExtra("numberphone");
        verificationID=intent.getStringExtra("verificationID");
    }

    private void addcontrols() {
        numberOTP = findViewById(R.id.numberOTP);
        btnComfirmOTP = findViewById(R.id.btnComfirmOTP);
        sendOTPAgain = findViewById(R.id.OTPAgain);
        mAuth = FirebaseAuth.getInstance();
        toolbar= findViewById(R.id.sendOTPToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                            PageMainCus.customer.setNumberPhone(Integer.parseInt(phone));

                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            PageMainCus.MyToast(getApplicationContext(),"That bai");

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void onClickAgain() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(numberPhone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setForceResendingToken(mForceResendingToke)// (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getApplicationContext(),"Number verification failed", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationID=s;
                                mForceResendingToke=forceResendingToken;
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
}