//package com.example.myapplication.Activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
////import com.example.myapplication.Adapter.PopularAdapter;
////import com.example.myapplication.Adapter.WishlistAdapter;
//import com.example.myapplication.R;
//import com.example.myapplication.databinding.ActivityMainBinding;
//import com.example.myapplication.databinding.ActivityWishlistBinding;
//import com.example.myapplication.domain.PopularDomain;
//import com.example.myapplication.domain.WishlistDomain;
//
//import java.util.ArrayList;
//
//public class WishlistActivity extends AppCompatActivity {
//
//    ActivityWishlistBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityWishlistBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//
//
//
//        statusBarColor();
//        initRecyclerView();
//    }
//
//    private void statusBarColor() {
//        Window window = WishlistActivity.this.getWindow();
//        window.setStatusBarColor(ContextCompat.getColor(WishlistActivity.this,R.color.purple_Dark));
//    }
//
//    private void initRecyclerView() {
//        ArrayList<WishlistDomain> items = new ArrayList<>();
//        items.add(new WishlistDomain("T-shirt black","item_1",15,4,500,"test1"));
//        items.add(new WishlistDomain("Smart Watch","item_2",10,4.5,450,"test2"));
//        items.add(new WishlistDomain("Phone","item_3",3,4.9,800,"test3"));
//        items.add(new WishlistDomain("monitor","item_4",3,4.9,800,"test3"));
//
//
//        binding.WishlistView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        binding.WishlistView.setAdapter(new WishlistAdapter(items));
//    }
//}