package com.example.myapplication.help;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.domain.PopularDomain;
import com.example.myapplication.domain.User;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1 = "CREATE TABLE users (useriD INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, email TEXT, password TEXT, adminpriority INTEGER DEFAULT 0)";
        String qry2 = "CREATE TABLE items (id INTEGER PRIMARY KEY, title TEXT, picUrl TEXT, review INTEGER, score REAL, price REAL, description TEXT)";
        String qry3 = "CREATE TABLE cart (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, picUrl TEXT, review INTEGER, score REAL, price REAL, description TEXT, NumberInCart INTEGER)";
        db.execSQL(qry1);
        db.execSQL(qry2);
        db.execSQL(qry3);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS items");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);

    }

    public void register(String username,String email,String password){
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("password",password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users",null,cv);
        db.close();

    }
    public int login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT useriD FROM users WHERE username = ? AND password = ?",
                new String[]{username, password});

        if (c.moveToFirst()) {
            int userId = c.getInt(c.getColumnIndexOrThrow("useriD"));
            c.close();
            return userId; // Возвращаем ID пользователя
        }

        c.close();
        return -1; // Если вход не успешен
    }
    public void get_item(int id, String title, String picUrl, int review, double score, double price, String description) {
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[7];
        str[0] = String.valueOf(id);
        str[1] = title;
        str[2] = picUrl;
        str[3] = String.valueOf(review);
        str[4] = String.valueOf(score);
        str[5] = String.valueOf(price);
        str[6] = description;
        Cursor c = db.rawQuery("select * from items where id=?",str);
    }
    public List<PopularDomain> getAllItems() {
        List<PopularDomain> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM items", null);

        if (c.moveToFirst()) {
            do {
                PopularDomain item = new PopularDomain(
                        c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("title")),
                        c.getString(c.getColumnIndexOrThrow("picUrl")),
                        c.getInt(c.getColumnIndexOrThrow("review")),
                        c.getDouble(c.getColumnIndexOrThrow("score")),
                        c.getDouble(c.getColumnIndexOrThrow("price")),
                        c.getString(c.getColumnIndexOrThrow("description"))
                );
                items.add(item);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return items;
    }

    public List<PopularDomain> getCartItems() {
        List<PopularDomain> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cart", null);

        if (c.moveToFirst()) {
            do {
                PopularDomain item = new PopularDomain(
                        c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("title")),
                        c.getString(c.getColumnIndexOrThrow("picUrl")),
                        c.getInt(c.getColumnIndexOrThrow("review")),
                        c.getDouble(c.getColumnIndexOrThrow("score")),
                        c.getDouble(c.getColumnIndexOrThrow("price")),
                        c.getString(c.getColumnIndexOrThrow("description"))
                );
                // Устанавливаем количество в корзине
                item.setNumberInCart(c.getInt(c.getColumnIndexOrThrow("NumberInCart")));
                items.add(item);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return items;
    }
    public void addItem(PopularDomain item) {
        ContentValues cv = new ContentValues();
        cv.put("title", item.getTitle());
        cv.put("picUrl", item.getPicUrl());
        cv.put("review", item.getReview());
        cv.put("score", item.getScore());
        cv.put("price", item.getPrice());
        cv.put("description", item.getDescription());
        cv.put("numberInCart", item.getNumberInCart());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart", null, cv);
        db.close();
    }

    public void updateItem(PopularDomain item) {
        ContentValues cv = new ContentValues();
        cv.put("numberInCart", item.getNumberInCart());
        cv.put("price", item.getPrice()); // Опционально
        cv.put("description", item.getDescription()); // Опционально
        SQLiteDatabase db = getWritableDatabase();
        db.update("cart", cv, "id = ?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public void deleteItem(int itemId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "id = ?", new String[]{String.valueOf(itemId)});
        db.close();
    }
    public void insertDefaultItems() {
        SQLiteDatabase db = getWritableDatabase();

        // Используем транзакции для повышения производительности
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO items (title, picUrl, review, score, price, description) " +
                    "VALUES ('T-shirt black', 'item_1', 15, 4.0, 20.0, 'A men''s T-shirt is a basic model for adult men and teenagers.')");

            db.execSQL("INSERT INTO items (title, picUrl, review, score, price, description) " +
                    "VALUES ('Smart Watch', 'item_2', 10, 4.2, 200.0, 'A novelty! Stylish and functional smart watch.')");

            db.execSQL("INSERT INTO items (title, picUrl, review, score, price, description) " +
                    "VALUES ('Phone', 'item_3', 3, 4.6, 320.0, 'A smartphone with powerful processor and capacious memory.')");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();
    }
    public List<PopularDomain> getItemsByPage(int pageNumber, int pageSize) {
        List<PopularDomain> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        int offset = (pageNumber - 1) * pageSize;
        Cursor c = db.rawQuery("SELECT * FROM items LIMIT ? OFFSET ?",
                new String[]{String.valueOf(pageSize), String.valueOf(offset)});

        if (c.moveToFirst()) {
            do {
                PopularDomain item = new PopularDomain(
                        c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("title")),
                        c.getString(c.getColumnIndexOrThrow("picUrl")),
                        c.getInt(c.getColumnIndexOrThrow("review")),
                        c.getDouble(c.getColumnIndexOrThrow("score")),
                        c.getDouble(c.getColumnIndexOrThrow("price")),
                        c.getString(c.getColumnIndexOrThrow("description"))
                );
                items.add(item);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return items;
    }
    public List<PopularDomain> getCartItemsByPage(int pageNumber, int pageSize) {
        List<PopularDomain> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        int offset = (pageNumber - 1) * pageSize;
        Cursor c = db.rawQuery("SELECT * FROM cart LIMIT ? OFFSET ?",
                new String[]{String.valueOf(pageSize), String.valueOf(offset)});

        if (c.moveToFirst()) {
            do {
                PopularDomain item = new PopularDomain(
                        c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("title")),
                        c.getString(c.getColumnIndexOrThrow("picUrl")),
                        c.getInt(c.getColumnIndexOrThrow("review")),
                        c.getDouble(c.getColumnIndexOrThrow("score")),
                        c.getDouble(c.getColumnIndexOrThrow("price")),
                        c.getString(c.getColumnIndexOrThrow("description"))
                );
                items.add(item);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return items;
    }

    public List<User> getUsersByPage(int pageNumber, int pageSize) {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        int offset = (pageNumber - 1) * pageSize;
        Cursor c = db.rawQuery("SELECT * FROM users LIMIT ? OFFSET ?",
                new String[]{String.valueOf(pageSize), String.valueOf(offset)});

        if (c.moveToFirst()) {
            do {
                User user = new User(
                        c.getInt(c.getColumnIndexOrThrow("useriD")),
                        c.getString(c.getColumnIndexOrThrow("username")),
                        c.getString(c.getColumnIndexOrThrow("email")),
                        c.getString(c.getColumnIndexOrThrow("password")),
                        c.getInt(c.getColumnIndexOrThrow("adminpriority"))
                );
                users.add(user);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return users;
    }
    public void insertUsers() {
        SQLiteDatabase db = getWritableDatabase();

        // Using transactions for performance
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO users (username, email, password, adminpriority) " +
                    "VALUES ('Wonder', 'ya.sir-len2013@mail.ru', 'vvtbond123$', 1)");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();
    }

}
