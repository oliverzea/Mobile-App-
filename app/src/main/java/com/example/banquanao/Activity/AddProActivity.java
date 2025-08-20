package com.example.banquanao.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.banquanao.R;
import com.example.banquanao.database.Category;
import com.example.banquanao.database.Product;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddProActivity extends AppCompatActivity {
    List<Product> listProduct;
    ImageView ivPAdd;
    EditText editTextNamePro,editTextPrice,editTextQuantity;
    Button btSave,btCate,btSize;;
    RelativeLayout relativeLayout;
    Toolbar toolbar;
    Category category;
    Bitmap seletedPhotoBitMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pro);
        addControl();
        addEvent();


    }


    private void addEvent() {
        btCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddProActivity.this, ListCateActivity.class);
                categoryActivityResult.launch(i);
            }
        });
        btSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddProActivity.this, ListSizeActivity.class);
                sizeActivityResult.launch(i);
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =editTextNamePro.getText().toString().trim();
                String price = editTextPrice.getText().toString().trim();
                String quantity = editTextQuantity.getText().toString().trim();

                if(name.equals("") || price.equals("")||quantity.equals("")){
                    Toast.makeText(getApplicationContext(), "Nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues values=new ContentValues();
                values.put("id_product",getIdMax()+1);
                values.put("name",editTextNamePro.getText().toString());
                values.put("price",editTextPrice.getText().toString()+"");
                values.put("id_Size",Integer.parseInt(btSize.getText().toString().trim()));
                values.put("id_Category",category.getId());
                values.put("quantity",editTextQuantity.getText().toString()+"");
//                byte [] imageByte = Utils.convertBitmapToByteArray(seletedPhotoBitMap);
//
//                if (imageByte.length > 0) {
//                    values.put("image1", imageByte);
//                }
                BitmapDrawable bitmapDrawable = (BitmapDrawable) ivPAdd.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                byte[] bytes = outputStream.toByteArray();
                if(bytes.length>0){
                    values.put("image1",bytes);
                }

                long kq= PageMainLoginActivity.database.insert("Product",null,values);
                if(kq>0)
                {
                     Toast.makeText(AddProActivity.this, "Insert success", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddProActivity.this, "Insert new recoerd fail", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }

        });
        ivPAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayBottomSheet();
            }

            private void displayBottomSheet() {
                // creating a variable for our bottom sheet dialog.
                final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(AddProActivity.this, com.google.android.material.R.style.Base_Theme_Material3_Light_BottomSheetDialog);

                // passing a layout file for our bottom sheet dialog.
                View layout = LayoutInflater.from(AddProActivity.this).inflate(R.layout.select_photo,relativeLayout, false);

                // passing our layout file to our bottom sheet dialog.
                bottomSheetTeachersDialog.setContentView(layout);

                // below line is to set our bottom sheet dialog as cancelable.
                bottomSheetTeachersDialog.setCancelable(false);

                // below line is to set our bottom sheet cancelable.
                bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
                bottomSheetTeachersDialog.setOnShowListener(dialogInterface -> {
                    BottomSheetDialog d = (BottomSheetDialog) dialogInterface;

                    FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                    BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                });

                // below line is to display our bottom sheet dialog.
                bottomSheetTeachersDialog.show();

                // initializing our text views and image views.
                Button btGallery = layout.findViewById(R.id.btnGallery);
                btGallery.setOnClickListener(view -> {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    imagePickerActivityResult.launch(photoPickerIntent);
                });
                Button btCapture = layout.findViewById(R.id.btnCamera);
                btCapture.setOnClickListener(view -> {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    imageCaptureActivityResult.launch(intent);
                });
                // creating a variable for document reference.

            }

        });
    }
    public int getIdMax(){
        String query = "select id_product from Product";
        Cursor c = PageMainLoginActivity.database.rawQuery(query,null);
        c.moveToFirst();
        int max = c.getInt(0);
        while (c.moveToNext()){
            if (max < c.getInt(0)) {
                max=c.getInt(0);

            }
        }
        return max;
    }
    private void addControl() {
        toolbar = findViewById(R.id.materialToolbarAddPro);
        btSave=findViewById(R.id.btnSave);
        editTextNamePro=findViewById(R.id.edtNamePro);
        editTextQuantity=findViewById(R.id.edtQuantity);
        editTextPrice=findViewById(R.id.edtPrice);
        btCate= findViewById(R.id.btnChooseCate);
        btSize= findViewById(R.id.btnChooseSize);
        ivPAdd=findViewById(R.id.ivProAdd);
        relativeLayout = findViewById(R.id.relativeLayout);
       // ivPAdd.setImageBitmap(Utils.loadBitmapFromAssets("", "select_avatar_placeholder.png"));
        listProduct = new ArrayList<>();
        category = new Category();

    }
    ActivityResultLauncher<Intent> categoryActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null) {
                Intent data = result.getData();
                 category = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    category = data.getSerializableExtra("Category_Item_Extra_Key_Name", Category.class);
                }
                btCate.setText(category.getName());
            }
        }
    });
    ActivityResultLauncher<Intent> sizeActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                            int pro = data.getIntExtra("Size_Item_Extra_Key_Name",0);
                            btSize.setText(pro+"");
                }
            });
    ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        Intent data = result.getData();
                        if(data.getData()!=null){
                            Uri imageUri = data.getData();
                            try {
                                seletedPhotoBitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                                ivPAdd.setImageBitmap(seletedPhotoBitMap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Log.e("S","Ko lay dc Uri");
                        }


                    }
                }
            }
    );

    ActivityResultLauncher<Intent> imageCaptureActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            seletedPhotoBitMap= (Bitmap) intent.getExtras().get("data");
                            ivPAdd.setImageBitmap(seletedPhotoBitMap);
                        }
                    }
                }
            }
    );

}