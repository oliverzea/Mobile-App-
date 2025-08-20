package com.example.banquanao.Fragment;

import static com.example.banquanao.Activity.PageMainCus.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.banquanao.Activity.PageMainCus;
import com.example.banquanao.R;
import com.example.banquanao.Adapter.PageMainAdapter;
import com.example.banquanao.database.Product;
import com.example.banquanao.ViewPagerImg.ImageViewPager;
import com.example.banquanao.ViewPagerImg.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.relex.circleindicator.CircleIndicator3;


public class HomeFragment extends Fragment {
    ViewPagerAdapter viewPagerAdapter;
    ViewPager2 viewPager2 ;
    List<ImageViewPager> list_imageViewPagers;
    CircleIndicator3 circleIndicator3;
    Handler handler = new Handler();
    public static Context context;
    public static int[] quantityOfProduct;
    public static SearchView searchView;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager2.getCurrentItem()==list_imageViewPagers.size()-1){
                viewPager2.setCurrentItem(0);
            }else{
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            }
        }
    };

    public static List<Product> mlistProduct,getPro;
    public static PageMainAdapter adapterPageMain;


    String TABLE_PRODUCT ="Product";
    String TABLE_SIZE ="Size";

    String QUERY_GET_PRODUCT_SIZE = "\n" +
            "select * \n" +
            "from Product p, Size s\n" +
            "where p.id_Size = s.id_Size\n";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Activity activity = new Activity();
        addControl(view);
        addImgViewPager2();
        mlistProduct=getListProduct();
        PageMainCus.backup_product = getListProduct();
        addItemPageMain();
        getQuantity();

        return view;
    }

    private void addControl(View view) {
        circleIndicator3 = view.findViewById(R.id.circleIndicator);
        viewPager2 = view.findViewById(R.id.viewPager2);
        list_imageViewPagers=getListImg();
        context = view.getContext();
        mlistProduct=new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyvlerViewPageMain);

    }

    private void getQuantity(){
        quantityOfProduct = new int[getMaxIdPro()+1];
        for(int i=0;i<mlistProduct.size();i++){
            quantityOfProduct[mlistProduct.get(i).getId()]=mlistProduct.get(i).getQuantity();
        }



    }

    private int getMaxIdPro(){
        int max = mlistProduct.get(0).getId();
        for(int i=1;i<mlistProduct.size();i++){
            if(max<mlistProduct.get(i).getId()){
                max=mlistProduct.get(i).getId();
            }
        }
        return max;
    }
    private List<ImageViewPager> getListImg() {
        List<ImageViewPager> list = new ArrayList<>();
        list.add(new ImageViewPager(R.drawable.d4));
        list.add(new ImageViewPager(R.drawable.d11));
        list.add(new ImageViewPager(R.drawable.ab5));
        list.add(new ImageViewPager(R.drawable.a16));
        list.add(new ImageViewPager(R.drawable.ab6));
        list.add(new ImageViewPager(R.drawable.ak8));
        list.add(new ImageViewPager(R.drawable.v14));

        return list;

    }
    public static void addItemPageMain(){

        adapterPageMain = new PageMainAdapter(mlistProduct,context);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterPageMain);

    }
    private List<Product> getListProduct() {

        List<Product> list = new ArrayList<>();
        Cursor cursor = PageMainCus.database.rawQuery(QUERY_GET_PRODUCT_SIZE,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            double price = cursor.getDouble(2);
            int quantity = cursor.getInt(8);
            byte[] idImage = cursor.getBlob(5);
            int id_category = cursor.getInt(4);
            double width = cursor.getDouble(10);
            double heigh = cursor.getDouble(11);
            double weight = cursor.getDouble(12);
            String sex = cursor.getString(13);
            String type = cursor.getString(14);
            list.add(new Product(id,name,price,idImage,quantity,width,heigh,weight,sex,type,id_category));
        }
        cursor.close();
        return list;

    }
    private void addImgViewPager2(){
        viewPagerAdapter = new ViewPagerAdapter(list_imageViewPagers);
        viewPager2.setAdapter(viewPagerAdapter);
        circleIndicator3.setViewPager(viewPager2);
        RunImg();
    }




    private void RunImg(){
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2000);
            }
        });
    }

    public static void getProductFormCategory(int id_cate){

        getPro= mlistProduct.stream().filter(product -> product.getId_category()==id_cate).collect(Collectors.toList());
        if(getPro.isEmpty()){
            Toast.makeText(context,"rong",Toast.LENGTH_SHORT).show();
            addItemPageMain();

        }else{
            adapterPageMain = new PageMainAdapter(getPro,context);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapterPageMain);
        }
    }

    public static void getProductBackup(List<Product> mlist){

        mlistProduct =mlist;
        if(mlistProduct.isEmpty()){
            Toast.makeText(context,"rong",Toast.LENGTH_SHORT).show();
            addItemPageMain();

        }else{
            adapterPageMain = new PageMainAdapter(mlistProduct,context);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapterPageMain);
            adapterPageMain.notifyDataSetChanged();

        }
    }



}