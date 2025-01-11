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

import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.Adapter.PopularAdapter;
import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.example.myapplication.help.DataBase;

import java.util.ArrayList;
import java.util.List;

public class AdminCartActivity extends AppCompatActivity {

    LinearLayout cartContainer;
    DataBase db;
    int currentPage = 1;
    final int PAGE_SIZE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cart);

        cartContainer = findViewById(R.id.cart_container);
        db = new DataBase(this, "onlineshopDB", null, 1);

        Button btnPrevious = findViewById(R.id.btn_previous);
        Button btnNext = findViewById(R.id.btn_next);

        loadCart(currentPage);

        btnPrevious.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                loadCart(currentPage);
            }
        });

        btnNext.setOnClickListener(v -> {
            List<PopularDomain> nextPageItems = db.getItemsByPage(currentPage + 1, PAGE_SIZE);

            if (!nextPageItems.isEmpty()) {
                currentPage++;
                loadCart(currentPage);
            }
        });

        setBackButton();
    }

    private void loadCart(int page) {
        // Очищаем старые данные
        cartContainer.removeAllViews();

        // Загружаем пользователей текущей страницы
        List<PopularDomain> cartItems = db.getCartItemsByPage(page, PAGE_SIZE);

        for (PopularDomain cartList : cartItems) {
            // Создаем View для каждого пользователя
            TextView cartView = new TextView(this);
            cartView.setText(String.format("ItemID: %s\n" +
                            "Title: %s\n" +
                            "PicUrl: %s\n" +
                            "Review: %s\n" +
                            "Score: %s\n" +
                            "Price: %s\n" +
                            "Description: %s\n"+
                            "Number in cart: %s",
                    cartList.getId(),cartList.getTitle(), cartList.getPicUrl(), cartList.getReview(), cartList.getScore(), cartList.getPrice(), cartList.getDescription(),cartList.getNumberInCart()));
            cartView.setPadding(16, 16, 16, 16);

            // Добавляем созданное View в контейнер
            cartContainer.addView(cartView);

            // Добавляем разделитель между пользователями
            View divider = new View(this);
            divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2));
            divider.setBackgroundColor(Color.GRAY);
            cartContainer.addView(divider);
        }
    }

    private void setBackButton() {
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());
    }
}