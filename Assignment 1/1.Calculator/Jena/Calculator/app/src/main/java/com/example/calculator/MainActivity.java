package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This project implements a simple calculator
 * @author Jonas Allali (2965826), Julian Blumenröther (2985877), Jena Satkunarajan (2965839)
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
    buttonAdd, buttonSub, buttonDiv, buttonMul, buttonEqu, buttonC, buttonComma;
    TextView textView;

    //enums to specify the supported operators
    enum OP {
        ADD,
        SUB,
        MULT,
        DIV,
        UNINITIALIZED
    }

    OP currOP = OP.UNINITIALIZED; //holds the currently selected operator

    boolean comma = true; //true if comma is allowed
    boolean operator = false; //true if an operator is allowed
    boolean equ = false; //true if "equal" was pressed


    float leftInput, rightInput, result = 0; //left and right input, result also functions as a (sort of) buffer
    String buffer = ""; //a buffer to hold intermediate inputs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        /**
         * Initialize buttons and set onClick behaviour.
         * Overwrite and use factory method for operand/operators, as they behave similarly.
         * "clear", "equal" and "comma" get special behaviour
         */
        button0 = findViewById(R.id.buttonZero);
        button0.setOnClickListener(this);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(this);
        button9 = findViewById(R.id.button9);
        button9.setOnClickListener(this);
        buttonAdd =  findViewById(R.id.buttonPlus);
        buttonAdd.setOnClickListener(this);
        buttonSub =  findViewById(R.id.buttonMinus);
        buttonSub.setOnClickListener(this);
        buttonMul =  findViewById(R.id.buttonMal);
        buttonMul.setOnClickListener(this);
        buttonDiv = findViewById(R.id.buttonGeteilt);
        buttonDiv.setOnClickListener(this);

        buttonC =  findViewById(R.id.buttonC);
        buttonEqu = findViewById(R.id.buttonGleich);
        buttonComma = findViewById(R.id.buttonKomma);

        buttonComma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!operator) {
                    //add leading zero if needed
                    buffer += "0";
                    textView.setText(textView.getText() + "0");
                    result = 0;
                }
                if(comma) {
                    //update buffer if comma is appended to most recent (intermed.) result
                    //we need this iff comma-button is pressed immediately after displaying result
                    if(buffer == "") {
                        buffer = Integer.toString((int) result);
                    }
                    buffer += ".";
                    textView.setText(textView.getText() + ".");
                    comma = false;
                }
                equ = false;
            }
        });


        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                buffer = "";
                operator = false;
                comma = true;
                equ = false;
                leftInput = 0;
                rightInput = 0;
                result = 0;
                currOP = OP.UNINITIALIZED;
            }
        });

        buttonEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(currOP == OP.UNINITIALIZED || buffer == "" || textView.length() <= 0) {
                        return;
                    }
                    rightInput = Float.parseFloat(buffer);

                    switch(currOP) {
                        case ADD:
                            result = leftInput + rightInput;
                            break;
                        case SUB:
                            result = leftInput - rightInput;
                            break;
                        case MULT:
                            result = leftInput * rightInput;
                            break;
                        case DIV:
                            result = leftInput/rightInput;
                            break;
                        default:
                            result = 0;
                            break;
                    }
                    //check if number is whole and display with or without decimal part
                    if(result % 1 == 0) {
                        textView.setText(Integer.toString((int) result));
                        comma = true;
                    } else {
                        textView.setText(Float.toString(result));
                        comma = false;
                    }
                    buffer = "";
                    leftInput = 0;
                    rightInput = 0;
                    operator = true;
                    equ = true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonZero:
                operandAction("0");
                break;
            case R.id.button1:
                operandAction("1");
                break;
            case R.id.button2:
                operandAction("2");
                break;
            case R.id.button3:
                operandAction("3");
                break;
            case R.id.button4:
                operandAction("4");
                break;
            case R.id.button5:
                operandAction("5");
                break;
            case R.id.button6:
                operandAction("6");
                break;
            case R.id.button7:
                operandAction("7");
                break;
            case R.id.button8:
                operandAction("8");
                break;
            case R.id.button9:
                operandAction("9");
                break;
            case R.id.buttonPlus:
                operatorAction(OP.ADD, "\u002B");
                break;
            case R.id.buttonMinus:
                operatorAction(OP.SUB, "\u002D");
                break;
            case R.id.buttonMal:
                operatorAction(OP.MULT, "\u00D7");
                break;
            case R.id.buttonGeteilt:
                operatorAction(OP.DIV, "\u00F7");
                break;
            default:
                return;
        }
    }

    private void operandAction(String operand) {
        if(equ) {
            //clear field after a finished calculation
            textView.setText("");
            result = 0;
            equ = false;
            currOP = OP.UNINITIALIZED;
        }
        buffer += operand;
        textView.setText(textView.getText() + operand);
        operator = true;
    }

    private void operatorAction(OP op, String symbol) {
        if(operator) {
            //this only occurs if calculation is proceeded on an intermediate result
            if(buffer == "") {
                if(result % 1 == 0) {
                    buffer = Integer.toString((int) result);
                } else {
                    buffer = Float.toString(result);
                }
                equ = false;
            }
            leftInput = Float.parseFloat(buffer + "");
            textView.setText(buffer + symbol);
            buffer = "";
            currOP = op;
            operator = false;
            comma = true;
        }
    }

}
