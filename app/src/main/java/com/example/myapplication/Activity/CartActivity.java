package com.example.myapplication.Activity;

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

import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCartBinding;
import com.example.myapplication.domain.PopularDomain;
import com.example.myapplication.help.ChangeNumberItemsListener;
import com.example.myapplication.help.ManagmentCart;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ManagmentCart managmentCart;
    private ActivityCartBinding binding;
    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        setVariable();
        initCart();
        calculatorCart();
        statusBarColor();
    }

    private void statusBarColor() {
        Window window = CartActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CartActivity.this, R.color.purple_Dark));
    }

    private void initCart() {
        ArrayList<PopularDomain> cartItems = managmentCart.getListCart();

        if (cartItems.isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scroll.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scroll.setVisibility(View.VISIBLE);
        }

        CartAdapter adapter = new CartAdapter(this, cartItems, this::calculatorCart);
        binding.cartView.setLayoutManager(new LinearLayoutManager(this));
        binding.cartView.setAdapter(adapter);
    }

    private void calculatorCart() {
        double percentTax = 0.2;
        double delivery = 10;
        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100) / 100;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }
}
