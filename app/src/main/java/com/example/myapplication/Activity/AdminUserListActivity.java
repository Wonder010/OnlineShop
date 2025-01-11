package com.example.myapplication.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.domain.User;
import com.example.myapplication.help.DataBase;

import java.util.List;

public class AdminUserListActivity extends AppCompatActivity {

    LinearLayout userContainer;
    DataBase db;
    int currentPage = 1;
    final int PAGE_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);

        userContainer = findViewById(R.id.user_container);
        db = new DataBase(this, "onlineshopDB", null, 1);

        Button btnPrevious = findViewById(R.id.btn_previous);
        Button btnNext = findViewById(R.id.btn_next);

        loadUsers(currentPage);

        btnPrevious.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                loadUsers(currentPage);
            }
        });

        btnNext.setOnClickListener(v -> {
            List<User> nextPageUsers = db.getUsersByPage(currentPage + 1, PAGE_SIZE);

            if (!nextPageUsers.isEmpty()) {
                currentPage++;
                loadUsers(currentPage);
            }
        });

        setBackButton();
    }

    private void loadUsers(int page) {
        // Очищаем старые данные
        userContainer.removeAllViews();

        // Загружаем пользователей текущей страницы
        List<User> users = db.getUsersByPage(page, PAGE_SIZE);

        for (User user : users) {
            // Создаем View для каждого пользователя
            TextView userView = new TextView(this);
            userView.setText(String.format("UserID: %s\nUser_Priority: %s\nUsername: %s\nEmail: %s\nPassword: %s",
                    user.getUserId(),user.getAdminpriority(),user.getUsername(), user.getEmail(), user.getPassword()));
            userView.setPadding(16, 16, 16, 16);

            // Добавляем созданное View в контейнер
            userContainer.addView(userView);

            // Добавляем разделитель между пользователями
            View divider = new View(this);
            divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2));
            divider.setBackgroundColor(Color.GRAY);
            userContainer.addView(divider);
        }
    }

    private void setBackButton() {
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());
    }
}
