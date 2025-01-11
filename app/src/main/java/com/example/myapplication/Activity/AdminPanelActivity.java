package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.PopularAdapter;
import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.example.myapplication.help.DataBase;

import java.util.ArrayList;
import java.util.List;

public class AdminPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Navigation();
    }

    private void Navigation(){
        Button cartadminbtn = findViewById(R.id.cartadminbtn);
        Button itemadminbtn = findViewById(R.id.itemadminbtn);
        Button userlistadminbtn = findViewById(R.id.userlistadminbtn);
        cartadminbtn.setOnClickListener(v -> startActivity(new Intent(AdminPanelActivity.this,AdminCartActivity.class)));
        itemadminbtn.setOnClickListener(v -> startActivity(new Intent(AdminPanelActivity.this,AdminItemsActivity.class)));
        userlistadminbtn.setOnClickListener(v -> startActivity(new Intent(AdminPanelActivity.this,AdminUserListActivity.class)));
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());
    }

}
