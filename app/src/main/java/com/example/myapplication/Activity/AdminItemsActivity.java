package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.example.myapplication.help.DataBase;

import java.util.List;

public class AdminItemsActivity extends AppCompatActivity {

    LinearLayout itemsContainer;
    DataBase db;
    int currentPage = 1;
    final int PAGE_SIZE = 2;

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

        Button btnAddUser = findViewById(R.id.btn_add_item);
        btnAddUser.setOnClickListener(v -> addItemDialog());


        setBackButton();
    }

    private void loadItems(int page) {
        // Очищаем старые данные
        itemsContainer.removeAllViews();

        // Загружаем пользователей текущей страницы
        List<PopularDomain> items = db.getItemsByPage(page, PAGE_SIZE);

        for (PopularDomain item : items) {
            // Создаем View для каждого пользователя
            LinearLayout itemLayout = new LinearLayout(this);
            TextView itemsView = new TextView(this);
            itemsView.setText(String.format("ItemID: %s\nTitle: %s\nPicUrl: %s\nReview: %s\nScore: %s\nPrice: %s\nDescription: %s",
                    item.getId(),item.getTitle(), item.getPicUrl(), item.getReview(), item.getScore(), item.getPrice(), item.getDescription()));
            itemsView.setPadding(16, 16, 16, 16);
            itemLayout.addView(itemsView);



            Button btnDelete = new Button(this);
            btnDelete.setText("Delete");
            btnDelete.setOnClickListener(v -> deleteItemDialog(item.getId()));
            itemLayout.addView(btnDelete);

            Button btnUpdate = new Button(this);
            btnUpdate.setText("Update");
            btnUpdate.setOnClickListener(v -> updateItemDialog(item));
            itemLayout.addView(btnUpdate);


            itemsContainer.addView(itemLayout); // Добавляем view



            // Добавляем разделитель между пользователями
            View divider = new View(this);
            divider.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 2));
            divider.setBackgroundColor(Color.GRAY);
            itemsContainer.addView(divider);
        }
    }

    private void addItemDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_item, null);
        builder.setView(dialogView);

        EditText ettitle = dialogView.findViewById(R.id.et_title);
        EditText etpicUrl = dialogView.findViewById(R.id.et_picUrl);
        EditText etreview = dialogView.findViewById(R.id.et_review);
        EditText etscore = dialogView.findViewById(R.id.et_score);
        EditText etprice = dialogView.findViewById(R.id.et_price);
        EditText etdescription = dialogView.findViewById(R.id.et_description);



        builder.setPositiveButton("Add", (dialog, which) -> {
                    String  title = ettitle.getText().toString();
                    String  picUrl = etpicUrl.getText().toString();
                    int  review = Integer.parseInt(etreview.getText().toString());
                    int  score = Integer.parseInt(etscore.getText().toString());
                    int  price = Integer.parseInt(etprice.getText().toString());
                    String  description = etdescription.getText().toString();

            PopularDomain item = new PopularDomain(0, title, picUrl, review, score, price, description);
            db.additem(item);

            loadItems(currentPage);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void deleteItemDialog(int itemId) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    db.deleteitem(itemId);
                    loadItems(currentPage);
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }

    private void updateItemDialog(PopularDomain item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_item, null);
        builder.setView(dialogView);

        EditText ettitle = dialogView.findViewById(R.id.et_title);
        EditText etpicUrl = dialogView.findViewById(R.id.et_picUrl);
        EditText etreview = dialogView.findViewById(R.id.et_review);
        EditText etscore = dialogView.findViewById(R.id.et_score);
        EditText etprice = dialogView.findViewById(R.id.et_price);
        EditText etdescription = dialogView.findViewById(R.id.et_description);

        ettitle.getText().toString();
        etpicUrl.getText().toString();
        etreview.getText().toString();
        etscore.getText().toString();
        etprice.getText().toString();
        etdescription.getText().toString();

        builder.setPositiveButton("Update", (dialog, which) -> {
            item.setTitle(ettitle.getText().toString());
            item.setPicUrl(etreview.getText().toString());
            item.setScore(Double.parseDouble(etscore.getText().toString()));
            item.setPrice(Double.parseDouble(etprice.getText().toString()));
            item.setDescription(etdescription.getText().toString());

            db.updateitem(item);

            loadItems(currentPage); // Перезагрузка списка пользователей
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
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