package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
    buttonAdd, buttonSub, buttonDiv, buttonMul, buttonEqu, buttonC, buttonComma;
    TextView textView;
    boolean add = false;
    boolean sub = false;
    boolean mul = false;
    boolean div = false;
    boolean any = false; //true if an operator already exists
    boolean comma = false; //true if comma already used in expression
    boolean equ = false; //true if "equal" was pressed
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
                    textView.setText("");
                    equ = false;
                }
                textView.setText(textView.getText() + "0");
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
            }
        });

        buttonComma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(equ) {
                    textView.setText("0");
                    equ = false;
                }
                if(!comma) {
                    textView.setText(textView.getText() + ".");
                    comma = true;
                }
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!any) {
                    leftInput = Float.parseFloat(textView.getText() + "");
                    textView.setText("");
                    add = true;
                    any = true;
                    comma = false;
                }
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!any) {
                    leftInput = Float.parseFloat(textView.getText() + "");
                    textView.setText("");
                    sub = true;
                    any = true;
                    comma = false;
                }
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!any) {
                    leftInput = Float.parseFloat(textView.getText() + "");
                    textView.setText("");
                    mul = true;
                    any = true;
                    comma = false;
                }
            }
        });

        buttonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!any) {
                    leftInput = Float.parseFloat(textView.getText() + "");
                    textView.setText("");
                    div = true;
                    any = true;
                    comma = false;
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
                any = false;
                comma = false;
                equ = false;
                leftInput = 0;
                rightInput = 0;
            }
        });

        buttonEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(any && textView.getText().length() > 0) {
                    rightInput = Float.parseFloat(textView.getText() + "");

                    if (add) {
                        textView.setText(rightInput + leftInput + "");
                        add = false;
                        any = false;
                    }

                    if (sub) {
                        textView.setText(leftInput - rightInput + "");
                        sub = false;
                        any = false;
                    }

                    if (mul) {
                        textView.setText(leftInput * rightInput + "");
                        mul = false;
                        any = false;
                    }

                    if (div) {
                        textView.setText(leftInput / rightInput + "");
                        div = false;
                        any = false;
                    }
                    equ = true;
                }
            }
        });
    }
}
