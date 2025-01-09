package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Adapter.PopularAdapter;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.domain.PopularDomain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        statusBarColor();
        initRecyclerView();
        bottomNavigation();
    }

    private void bottomNavigation() {
        binding.cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CartActivity.class)));
        binding.explorerBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,ExplorerActivity.class)));
        binding.wishlistBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,WishlistActivity.class)));
        binding.loginBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,LoginActivity.class)));
    }


    private void statusBarColor() {
        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.purple_Dark));
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("T-shirt black","item_1",15,4,20,"A men's T-shirt is a basic model for adult men and teenagers.\n" +
                " A comfortable and lightweight men's T-shirt is perfect for spring and summer.\n" +
                " Suitable for tall guys and men of any age."));
        items.add(new PopularDomain("Smart Watch","item_2",10,4.2,200,"A novelty! Stylish and functional smart watch, will be a great accessory for your everyday look.\n" +
                "Smart watches not only track your activity and health, but also provide a wide range of features: Bluetooth BT5.2, heart rate monitor, blood pressure and oxygen level measurement,\n" +
                " call and message notifications, sleep monitoring, pedometer calculator, allways on display, flashlight, Chat GPT, built-in memory with music storage, voice recorder, compass and other new features."));
        items.add(new PopularDomain("Phone","item_3",3,4.6,320,"The realme Note 50 3/64GB RMX3834 smartphone in midnight black color offers users a powerful processor, capacious memory and many functions for comfortable use.\n" +
                "The Unisoc Tiger T612 processor with 8 cores and a frequency of 1.8 GHz ensures fast and smooth operation of the smartphone.\n" +
                "The smartphone is equipped with 64GB of internal memory and 3 GB of RAM, which allows you to store a large amount of data and applications. A memory card slot is also provided, which makes it possible to expand the device's memory.\n" +
                "The smartphone supports 4G LTE, 3G and 2G communication standards, as well as Wi-Fi 802.11ac and Bluetooth 5.0."));
        items.add(new PopularDomain("Monitor","item_4",26,4.9,800,"Xiaomi Display G24 Fast IPS PC Gaming Monitor is ideal for working with computers, laptops and game consoles.\n" +
                " Full HD resolution (1920 x 1080) provides a detailed and high-quality image.\n" +
                " The 23.8-inch G24 IPS screen is optimal for full immersion in the gameplay, providing a wide overview of the game interface."));


    binding.PopularView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    binding.PopularView.setAdapter(new PopularAdapter(items));
    }

}