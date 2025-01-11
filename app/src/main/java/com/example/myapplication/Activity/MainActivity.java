package com.example.myapplication.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.help.DataBase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "MainActivity started");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int userId = getIntent().getIntExtra("USER_ID", -1);
        Log.d("MainActivity", "Received userId: " + userId);

        if (userId != -1) {
            Log.e("MainActivity", "Invalid USER_ID. Redirecting to LoginActivity.");
            // Используем userID (например, проверка прав администратора)
            if (!isAdmin(userId)) {
                binding.adminpanelbtn.setVisibility(View.GONE);
            } else {
                binding.adminpanelbtn.setVisibility(View.VISIBLE);
            }
        }

        statusBarColor();
        initRecyclerView();
        bottomNavigation();
        upNavigation();

    }

    private void bottomNavigation() {
        binding.cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CartActivity.class)));
        binding.explorerBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,ExplorerActivity.class)));
//        binding.wishlistBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,WishlistActivity.class)));
        binding.loginBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,LoginActivity.class)));
    }
    private void upNavigation(){
        binding.adminpanelbtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,AdminPanelActivity.class)));
    }

    private boolean isAdmin(int userId) {
        DataBase db = new DataBase(this, "onlineshopDB", null, 1);
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT adminpriority FROM users WHERE useriD = ?",
                new String[]{String.valueOf(userId)});

        boolean isAdmin = false;
        if (cursor.moveToFirst()) {
            isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow("adminpriority")) == 1;
        }
        cursor.close();
        database.close();
        return isAdmin;
    }

    private void statusBarColor() {
        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.purple_Dark));
    }

    private void initRecyclerView() {
        // Получение данных из базы
        DataBase db = new DataBase(this, "onlineshopDB", null, 1);

        List<PopularDomain> items = db.getAllItems();


        // Настройка RecyclerView
        binding.PopularView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.PopularView.setAdapter(new PopularAdapter(this,new ArrayList<>(items)));
    }
}