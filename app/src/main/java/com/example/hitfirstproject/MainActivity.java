package com.example.hitfirstproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private int firstNumber;
    private int secondNumber;
    private char receivedOperand = '$';
    private static final String ERROR_MSG_NO_INPUT = "Error, press a number..";

    private static final String ERROR_MSG_NO_OPERAND = "Error, press an operand..";

    private static final String ERROR_MSG_DIVIDE_BY_ZERO = "Error, divide by zero..";

    private static final int DEFAULT_VALUE = -999;


    private boolean isErrorDisplayed()
    {
        return result.getText().toString().equals(ERROR_MSG_NO_INPUT) ||
                result.getText().toString().equals(ERROR_MSG_DIVIDE_BY_ZERO) ||
                result.getText().toString().equals(ERROR_MSG_NO_OPERAND);
    }
    private void resetCalculator(String message) {
        firstNumber = DEFAULT_VALUE;
        secondNumber = DEFAULT_VALUE;
        receivedOperand = '$';
        result.setText(message);
    }

    private int getNumberFromResult()
    {
        if(result.getText().toString().isEmpty()) return DEFAULT_VALUE;

        return Integer.parseInt(result.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        result = findViewById(R.id.textViewResult);

        resetCalculator("");
    }

    public void numberFunc(View view) {

        if(isErrorDisplayed())
        {
            resetCalculator("");
        }

        Button button = (Button) view;

        result.append(button.getText().toString());
    }

    public void resultFunc(View view) {
        if(receivedOperand == '$') {
            resetCalculator(ERROR_MSG_NO_OPERAND);
            return;
        }

        if(firstNumber == DEFAULT_VALUE)
        {
            resetCalculator(ERROR_MSG_NO_INPUT);
            return;
        }

        secondNumber = getNumberFromResult();
        int resultNumeric = 0;

        switch (receivedOperand) {
            case '+':
                resultNumeric = firstNumber + secondNumber;
                break;
            case '-':
                resultNumeric = firstNumber - secondNumber;
                break;
            case '*':
                resultNumeric = firstNumber * secondNumber;
                break;
            case '/':
                if (secondNumber != 0) {
                    resultNumeric = firstNumber / secondNumber;
                } else {
                    result.setText(ERROR_MSG_DIVIDE_BY_ZERO);
                    return;
                }
                break;
        }

        resetCalculator(String.valueOf(resultNumeric));
    }

    public void operandFunc(View view) {
        Button button = (Button) view;
        firstNumber = getNumberFromResult();

        if(firstNumber == DEFAULT_VALUE)
        {
            resetCalculator(ERROR_MSG_NO_INPUT);
            return;
        }

        result.setText("");
        receivedOperand = button.getText().charAt(0);
    }

    public void deleteFunc(View view) {
        resetCalculator("");
    }

    public void deleteDigitFunc(View view) {
        if(result.getText().toString().isEmpty()) return;

        if(isErrorDisplayed())
        {
            resetCalculator("");
        }

        int numericResult = Integer.parseInt(result.getText().toString());

        if(numericResult < 10) {
            result.setText("");
            return;
        }

        numericResult /= 10;
        result.setText(String.valueOf(numericResult));
    }
}