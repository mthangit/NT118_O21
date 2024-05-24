package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText heightInput;
    private EditText weightInput;
    private TextView bmiResult;
    private Button calculateButton;
    private Button clearButton;
    private Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        bmiResult = findViewById(R.id.bmiResult);
        calculateButton = findViewById(R.id.calculateButton);
        clearButton = findViewById(R.id.clearButton);
        closeButton = findViewById(R.id.closeButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInputs();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void calculateBMI() {
        String heightStr = heightInput.getText().toString();
        String weightStr = weightInput.getText().toString();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            return;
        }

        // Convert height from centimeters to meters
        double heightInMeters = Double.parseDouble(heightStr) / 100;

        double weight = Double.parseDouble(weightStr);

        double bmi = weight / (heightInMeters * heightInMeters);
        String bmiResultText;

        if (bmi >= 25) {
            bmiResultText = "Béo phì: " + String.format("%.2f", bmi);
        } else if (bmi >= 18.5) {
            bmiResultText = "Bình thường: " + String.format("%.2f", bmi);
        } else {
            bmiResultText = "Suy dinh dưỡng: " + String.format("%.2f", bmi);
        }
        bmiResult.setText(bmiResultText);
    }

    private void clearInputs() {
        heightInput.setText("");
        weightInput.setText("");
        bmiResult.setText("");
    }
}
