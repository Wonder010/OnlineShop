package com.example.myapplication.help;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.domain.PopularDomain;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1 = "CREATE TABLE users (username TEXT, email TEXT, password TEXT)";
        String qry2 = "CREATE TABLE items (id INTEGER PRIMARY KEY, title TEXT, picUrl TEXT, review INTEGER, score REAL, price REAL, description TEXT)";
        String qry3 = "CREATE TABLE cart (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, picUrl TEXT, review INTEGER, score REAL, price REAL, description TEXT, numberInCart INTEGER)";
        db.execSQL(qry1);
        db.execSQL(qry2);
        db.execSQL(qry3);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
    public int login(String username,String password) {
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username=? and password=?",str);
        if(c.moveToFirst()){
            result=1;
        }
        return result;
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
        SQLiteDatabase db = getWritableDatabase();
        db.update("cart", cv, "title = ?", new String[]{item.getTitle()});
        db.close();
    }

    public void deleteItem(int itemId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "id = ?", new String[]{String.valueOf(itemId)});
        db.close();
    }

}
