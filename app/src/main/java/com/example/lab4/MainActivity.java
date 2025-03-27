package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView randomNumberTextView, reversedTextView, completedTaskTextView, colorLabelTextView;
    private Button randomNumberButton, reverseTextButton, startStopButton, orderButton;
    private EditText inputTextField;
    private CheckBox shopTaskCheckBox, lunchTaskCheckBox, bookTaskCheckBox;
    private RadioGroup breadRadioGroup, drinkRadioGroup;
    private Spinner colorSpinner;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomNumberTextView = findViewById(R.id.randomNumberTextView);
        randomNumberButton = findViewById(R.id.randomNumberButton);
        inputTextField = findViewById(R.id.inputTextField);
        reversedTextView = findViewById(R.id.reversedTextView);
        reverseTextButton = findViewById(R.id.reverseTextButton);
        startStopButton = findViewById(R.id.startStopButton);
        shopTaskCheckBox = findViewById(R.id.shopTaskCheckBox);
        lunchTaskCheckBox = findViewById(R.id.lunchTaskCheckBox);
        bookTaskCheckBox = findViewById(R.id.bookTaskCheckBox);
        completedTaskTextView = findViewById(R.id.completedTaskTextView);
        breadRadioGroup = findViewById(R.id.breadRadioGroup);
        drinkRadioGroup = findViewById(R.id.drinkRadioGroup);
        orderButton = findViewById(R.id.orderButton);
        colorSpinner = findViewById(R.id.colorSpinner);
        colorLabelTextView = findViewById(R.id.colorLabelTextView);

        // Генерация случайного числа
        randomNumberButton.setOnClickListener(v -> {
            Random random = new Random();
            int number = random.nextInt(100) + 1;
            randomNumberTextView.setText(String.valueOf(number));
        });

        // Инвертирование текста
        reverseTextButton.setOnClickListener(v -> {
            String text = inputTextField.getText().toString();
            String reversedText = new StringBuilder(text).reverse().toString();
            reversedTextView.setText(reversedText);
        });

        // Смена состояния кнопки Старт/Стоп
        startStopButton.setOnClickListener(v -> {
            isRecording = !isRecording;
            if (isRecording) {
                startStopButton.setText("Стоп");
            } else {
                startStopButton.setText("Старт");
            }
        });


        shopTaskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateCompletedTasks());
        lunchTaskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateCompletedTasks());
        bookTaskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> updateCompletedTasks());

        // Отправка заказа
        orderButton.setOnClickListener(v -> {
            int selectedBreadId = breadRadioGroup.getCheckedRadioButtonId();
            int selectedDrinkId = drinkRadioGroup.getCheckedRadioButtonId();

            RadioButton selectedBreadRadioButton = findViewById(selectedBreadId);
            RadioButton selectedDrinkRadioButton = findViewById(selectedDrinkId);

            String orderSummary = "Ваш заказ: \n" +
                    "Хлеб: " + selectedBreadRadioButton.getText() + "\n" +
                    "Напиток: " + selectedDrinkRadioButton.getText();
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("orderSummary", orderSummary);
            startActivity(intent);
        });

        // Настройка Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedColor = parentView.getItemAtPosition(position).toString();
                colorLabelTextView.setTextColor(getColorByName(selectedColor));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void updateCompletedTasks() {
        StringBuilder tasks = new StringBuilder();
        if (shopTaskCheckBox.isChecked()) {
            tasks.append("Сходить в магазин\n");
        }
        if (lunchTaskCheckBox.isChecked()) {
            tasks.append("Пообедать\n");
        }
        if (bookTaskCheckBox.isChecked()) {
            tasks.append("Дочитать книгу\n");
        }
        completedTaskTextView.setText(tasks.toString());
    }

    private int getColorByName(String colorName) {
        switch (colorName.toLowerCase()) {
            case "красный":
                return getResources().getColor(android.R.color.holo_red_light);
            case "жёлтый":
                return getResources().getColor(android.R.color.holo_orange_light);
            case "синий":
                return getResources().getColor(android.R.color.holo_blue_light);
            default:
                return getResources().getColor(android.R.color.black);
        }
    }
}
