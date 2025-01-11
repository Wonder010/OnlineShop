package com.example.myapplication.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.PopularAdapter;
import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.example.myapplication.domain.User;
import com.example.myapplication.help.DataBase;

import java.util.ArrayList;
import java.util.List;

public class AdminItemsActivity extends AppCompatActivity {

    LinearLayout itemsContainer;
    DataBase db;
    int currentPage = 1;
    final int PAGE_SIZE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_items);

        itemsContainer = findViewById(R.id.items_container);
        db = new DataBase(this, "onlineshopDB", null, 1);

        Button btnPrevious = findViewById(R.id.btn_previous);
        Button btnNext = findViewById(R.id.btn_next);

        loadItems(currentPage);

        btnPrevious.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                loadItems(currentPage);
            }
        });

        btnNext.setOnClickListener(v -> {
            List<PopularDomain> nextPageItems = db.getItemsByPage(currentPage + 1, PAGE_SIZE);

            if (!nextPageItems.isEmpty()) {
                currentPage++;
                loadItems(currentPage);
            }
        });

        setBackButton();
    }

    private void loadItems(int page) {
        // Очищаем старые данные
        itemsContainer.removeAllViews();

        // Загружаем пользователей текущей страницы
        List<PopularDomain> items = db.getItemsByPage(page, PAGE_SIZE);

        for (PopularDomain item : items) {
            // Создаем View для каждого пользователя
            TextView itemsView = new TextView(this);
            itemsView.setText(String.format("ItemID: %s\n" +
                            "Title: %s\n" +
                            "PicUrl: %s\n" +
                            "Review: %s\n" +
                            "Score: %s\n" +
                            "Price: %s\n" +
                            "Description: %s",
                    item.getId(),item.getTitle(), item.getPicUrl(), item.getReview(), item.getScore(), item.getPrice(), item.getDescription()));
            itemsView.setPadding(16, 16, 16, 16);

            // Добавляем созданное View в контейнер
            itemsContainer.addView(itemsView);

            // Добавляем разделитель между пользователями
            View divider = new View(this);
            divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2));
            divider.setBackgroundColor(Color.GRAY);
            itemsContainer.addView(divider);
        }
    }

    private void setBackButton() {
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Загружаем элементы для текущей страницы
        loadItems(currentPage);
    }
}