package com.example.myapplication.help;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.domain.PopularDomain;

import java.util.ArrayList;
import java.util.List;

public class ManagmentCart {
    private Context context;
    private DataBase dataBase;

    public ManagmentCart(Context context) {
        this.context = context;
        this.dataBase = new DataBase(context, "onlineshopDB", null, 1); // Initialize Database
    }

    // Get the list of cart items from the database
    public ArrayList<PopularDomain> getListCart() {
        List<PopularDomain> cartItems = dataBase.getCartItems();
        return new ArrayList<>(cartItems);
    }

    // Adds or updates item quantity in the cart
    public void insertFood(PopularDomain item) {
        List<PopularDomain> cartList = getListCart();
        boolean exists = false;

        for (PopularDomain existingItem : cartList) {
            if (existingItem.getTitle().equals(item.getTitle())) {
                existingItem.setNumberInCart(existingItem.getNumberInCart() + item.getNumberInCart());
                dataBase.updateItem(existingItem);
                exists = true;
                break;
            }
        }

        if (!exists) {
            dataBase.addItem(item);
        }

        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public Double getTotalFee() {
        List<PopularDomain> cartList = getListCart();
        double totalFee = 0;
        for (PopularDomain item : cartList) {
            totalFee += (item.getPrice() * item.getNumberInCart());
        }
        return totalFee;
    }

    // Decreases the number of items in cart
    public void minusNumberItem(ArrayList<PopularDomain> items, int position, ChangeNumberItemsListener listener) {
        PopularDomain item = items.get(position);
        if (item.getNumberInCart() == 1) {
            dataBase.deleteItem(item.getId());
            items.remove(position);
        } else {
            item.setNumberInCart(item.getNumberInCart() - 1);
            dataBase.updateItem(item);
        }
        listener.change();
    }

    // Increases the number of items in cart
    public void plusNumberItem(ArrayList<PopularDomain> items, int position, ChangeNumberItemsListener listener) {
        PopularDomain item = items.get(position);
        item.setNumberInCart(item.getNumberInCart() + 1);
        dataBase.updateItem(item);
        listener.change();
    }
}


