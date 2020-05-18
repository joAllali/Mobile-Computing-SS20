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
public class MainActivity extends AppCompatActivity {

    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
    buttonAdd, buttonSub, buttonDiv, buttonMul, buttonEqu, buttonC, buttonComma;
    TextView textView;

    //true if specific operator was used
    boolean add = false;
    boolean sub = false;
    boolean mul = false;
    boolean div = false;

    boolean comma = true; //true if comma is allowed
    boolean equ = false; //true if "equal" was pressed
    boolean operator = false; //true if an operator is allowed

    float leftInput, rightInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        //Initialize buttons
        button0 = findViewById(R.id.buttonZero);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonAdd =  findViewById(R.id.buttonPlus);
        buttonSub =  findViewById(R.id.buttonMinus);
        buttonMul =  findViewById(R.id.buttonMal);
        buttonDiv = findViewById(R.id.buttonGeteilt);
        buttonC =  findViewById(R.id.buttonC);
        buttonEqu = findViewById(R.id.buttonGleich);
        buttonComma = findViewById(R.id.buttonKomma);



        //Set OnclickListeners
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    //clear field after a finished calculation
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "0");
                operator = true;
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "1");
                operator = true;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "2");
                operator = true;
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "3");
                operator = true;
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "4");
                operator = true;
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "5");
                operator = true;
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "6");
                operator = true;
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "7");
                operator = true;
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "8");
                operator = true;
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "9");
                operator = true;
            }
        });

        buttonComma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!operator || equ) {
                    //add leading zero
                    textView.setText("0");
                    equ = false;
                }
                if(comma) {
                    textView.setText(textView.getText() + ".");
                    comma = false;
                }
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operator) {
                    leftInput = Float.parseFloat(textView.getText() + "");
                    textView.setText("");
                    add = true;
                    operator = false;
                    comma = true;
                }
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operator) {
                    leftInput = Float.parseFloat(textView.getText() + "");
                    textView.setText("");
                    sub = true;
                    operator = false;
                    comma = true;
                }
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operator) {
                    leftInput = Float.parseFloat(textView.getText() + "");
                    textView.setText("");
                    mul = true;
                    operator = false;
                    comma = true;
                }
            }
        });

        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operator) {
                    leftInput = Float.parseFloat(textView.getText() + "");
                    textView.setText("");
                    div = true;
                    operator = false;
                    comma = true;
                }
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                add = false;
                sub = false;
                mul = false;
                div = false;
                operator = false;
                comma = true;
                equ = false;
                leftInput = 0;
                rightInput = 0;
            }
        });

        buttonEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (add && textView.length() > 0) {
                        rightInput = Float.parseFloat(textView.getText() + "");
                        textView.setText(rightInput + leftInput + "");
                        add = false;
                        operator = true;
                        equ = true;
                        comma = true;
                    }

                    if (sub && textView.length() > 0) {
                        rightInput = Float.parseFloat(textView.getText() + "");
                        textView.setText(leftInput - rightInput + "");
                        sub = false;
                        operator = true;
                        equ = true;
                        comma = true;
                    }

                    if (mul && textView.length() > 0) {
                        rightInput = Float.parseFloat(textView.getText() + "");
                        textView.setText(leftInput * rightInput + "");
                        mul = false;
                        operator = true;
                        equ = true;
                        comma = true;
                    }

                    if (div && textView.length() > 0) {
                        rightInput = Float.parseFloat(textView.getText() + "");
                        textView.setText(leftInput / rightInput + "");
                        div = false;
                        operator = true;
                        equ = true;
                        comma = true;
                    }
            }
        });
    }
}
