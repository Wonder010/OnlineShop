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
        this.dataBase = new DataBase(context, "onlineshopDB", null, 1); // Создаем или открываем базу данных
    }

    public void insertFood(PopularDomain item) {
        List<PopularDomain> cartList = getListCart();
        boolean exists = false;

        for (PopularDomain existingItem : cartList) {
            if (existingItem.getTitle().equals(item.getTitle())) {
                // Увеличиваем количество вместо перезаписи
                existingItem.setNumberInCart(existingItem.getNumberInCart() + item.getNumberInCart());
                dataBase.updateItem(existingItem);
                exists = true;
                break;
            }
        }

        if (!exists) {
            // Добавляем новый элемент в корзину
            dataBase.addItem(item);
        }

        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<PopularDomain> getListCart() {
        // Получаем список элементов корзины из базы данных
        List<PopularDomain> items = dataBase.getCartItems(); // Предполагается, что этот метод возвращает только элементы корзины
        return new ArrayList<>(items);
    }

    public Double getTotalFee() {
        List<PopularDomain> listItem = getListCart();
        double fee = 0;
        for (PopularDomain item : listItem) {
            fee += (item.getPrice() * item.getNumberInCart());
        }
        return fee;
    }

    public void minusNumberItem(ArrayList<PopularDomain> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        PopularDomain item = listItem.get(position);
        if (item.getNumberInCart() == 1) {
            // Удаляем элемент из базы данных и списка
            dataBase.deleteItem(item.getId());
            listItem.remove(position);
        } else {
            // Уменьшаем количество элемента и обновляем запись
            item.setNumberInCart(item.getNumberInCart() - 1);
            Log.d("CartUpdate", "Before update: " + item.getNumberInCart());
            dataBase.updateItem(item);
            Log.d("CartUpdate", "After update: " + item.getNumberInCart());
        }

        changeNumberItemsListener.change();
    }

    public void plusNumberItem(ArrayList<PopularDomain> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        PopularDomain item = listItem.get(position);
        // Увеличиваем количество элемента
        item.setNumberInCart(item.getNumberInCart() + 1);
        Log.d("CartUpdate", "Before update: " + item.getNumberInCart());
        dataBase.updateItem(item);
        Log.d("CartUpdate", "After update: " + item.getNumberInCart());
        changeNumberItemsListener.change();
    }
}

