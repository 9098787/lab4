package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView orderSummaryTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Инициализация элементов UI
        orderSummaryTextView = findViewById(R.id.orderSummaryTextView);
        backButton = findViewById(R.id.backButton);

        // Получаем данные из Intent
        Intent intent = getIntent();
        String orderSummary = intent.getStringExtra("orderSummary");  // исправили на правильный ключ

        // Устанавливаем полученные данные в TextView
        orderSummaryTextView.setText(orderSummary);

        // Обработка кнопки "Назад"
        backButton.setOnClickListener(v -> {
            finish(); // Закрыть SecondActivity и вернуться в MainActivity
        });
    }
}
