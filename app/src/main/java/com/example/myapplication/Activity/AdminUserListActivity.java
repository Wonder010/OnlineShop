package com.example.myapplication.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.domain.User;
import com.example.myapplication.help.DataBase;

import java.util.List;

public class AdminUserListActivity extends AppCompatActivity {

    LinearLayout userContainer;
    DataBase db;
    int currentPage = 1;
    final int PAGE_SIZE = 3;

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

        Button btnAddUser = findViewById(R.id.btn_add_user);
        btnAddUser.setOnClickListener(v -> addUserDialog());



        setBackButton();
    }

    private void loadUsers(int page) {
        // Очищаем старые данные
        userContainer.removeAllViews();

        // Загружаем пользователей текущей страницы
        List<User> users = db.getUsersByPage(page, PAGE_SIZE);

        for (User user : users) {
            // Создаем View для каждого пользователя
            LinearLayout userLayout = new LinearLayout(this);
            userLayout.setOrientation(LinearLayout.VERTICAL);
            userLayout.setPadding(16, 16, 16, 16);

            TextView userView = new TextView(this);
            userView.setText(String.format(
                    "UserID: %s\nUser_Priority: %s\nUsername: %s\nEmail: %s\nPassword: %s",
                    user.getUserId(), user.getAdminpriority(), user.getUsername(), user.getEmail(), user.getPassword()
            ));
            userLayout.addView(userView);

            // Добавляем кнопки действий
            Button btnDelete = new Button(this);
            btnDelete.setText("Delete");
            btnDelete.setOnClickListener(v -> deleteUserDialog(user.getUserId()));
            userLayout.addView(btnDelete);

            Button btnUpdate = new Button(this);
            btnUpdate.setText("Update");
            btnUpdate.setOnClickListener(v -> updateUserDialog(user));
            userLayout.addView(btnUpdate);

            // Добавляем Layout пользователя в контейнер
            userContainer.addView(userLayout);

            // Добавляем разделитель между пользователями
            View divider = new View(this);
            divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 2
            ));
            divider.setBackgroundColor(Color.GRAY);
            userContainer.addView(divider);
        }
    }

    private void addUserDialog() {
        // Создаем диалог для ввода информации о пользователе
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_user, null);
        builder.setView(dialogView);

        EditText etUsername = dialogView.findViewById(R.id.et_username);
        EditText etEmail = dialogView.findViewById(R.id.et_email);
        EditText etPassword = dialogView.findViewById(R.id.et_password);
        EditText etAdminPriority = dialogView.findViewById(R.id.et_adminpriority);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            int adminPriority = Integer.parseInt(etAdminPriority.getText().toString());

            User user = new User(0, username, email, password, adminPriority);
            db.addUser(user);

            loadUsers(currentPage); // Перезагрузка списка пользователей
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void deleteUserDialog(int userId) {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    db.deleteUser(userId);
                    loadUsers(currentPage); // Перезагрузка списка пользователей
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }

    private void updateUserDialog(User user) {
        // Создаем диалог для обновления информации о пользователе
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_user, null);
        builder.setView(dialogView);

        EditText etUsername = dialogView.findViewById(R.id.et_username);
        EditText etEmail = dialogView.findViewById(R.id.et_email);
        EditText etPassword = dialogView.findViewById(R.id.et_password);
        EditText etAdminPriority = dialogView.findViewById(R.id.et_adminpriority);

        etUsername.setText(user.getUsername());
        etEmail.setText(user.getEmail());
        etPassword.setText(user.getPassword());
        etAdminPriority.setText(String.valueOf(user.getAdminpriority()));

        builder.setPositiveButton("Update", (dialog, which) -> {
            user.setUsername(etUsername.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.setPassword(etPassword.getText().toString());
            user.setAdminpriority(Integer.parseInt(etAdminPriority.getText().toString()));

            db.updateUser(user);

            loadUsers(currentPage); // Перезагрузка списка пользователей
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void setBackButton() {
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());
    }
}
