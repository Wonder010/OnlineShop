package com.example.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.help.DataBase;

public class LoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button loginButton;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        loginButton = findViewById(R.id.loginButton);
        tv = findViewById(R.id.textViewNewUser);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                DataBase db = new DataBase(getApplicationContext(),"onlineshopDB",null,1);
                db.insertUsers();
                db.insertDefaultItems();
                db.populateUsersTable();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill all details",Toast.LENGTH_SHORT).show();
                }else{
                    int userId = db.login(username, password);
                    if (userId != -1) {
                        // Успешный вход
                        Log.d("LoginActivity", "Login successful, userId: " + userId);
                        Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();

                        // Сохраняем имя пользователя для дальнейшего использования
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("USER_ID", userId);
                        startActivity(intent);

                    }else{
                        Log.d("LoginActivity", "Login failed. Invalid username/password.");
                        Toast.makeText(getApplicationContext(),"Invalid Username and Password",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });



    }
}