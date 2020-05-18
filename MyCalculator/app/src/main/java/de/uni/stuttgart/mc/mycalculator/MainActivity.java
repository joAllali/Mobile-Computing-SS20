package de.uni.stuttgart.mc.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView view;
    private StringBuilder value;
    private enum OPs {ADD, SUB, MULT, DIV};
    private OPs currOp;
    private Stack opStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.textView);
        findViewById(R.id.button_0).setOnClickListener(this);
        findViewById(R.id.button_1).setOnClickListener(this);
        findViewById(R.id.button_2).setOnClickListener(this);
        findViewById(R.id.button_3).setOnClickListener(this);
        findViewById(R.id.button_4).setOnClickListener(this);
        findViewById(R.id.button_5).setOnClickListener(this);
        findViewById(R.id.button_6).setOnClickListener(this);
        findViewById(R.id.button_7).setOnClickListener(this);
        findViewById(R.id.button_8).setOnClickListener(this);
        findViewById(R.id.button_9).setOnClickListener(this);
        findViewById(R.id.button_plus).setOnClickListener(this);
        findViewById(R.id.button_minus).setOnClickListener(this);
        findViewById(R.id.button_mult).setOnClickListener(this);
        findViewById(R.id.button_div).setOnClickListener(this);
        findViewById(R.id.button_equal).setOnClickListener(this);
        findViewById(R.id.button_comma).setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_del).setOnClickListener(this);
        value = new StringBuilder("_");
        opStack = new Stack();
        currOp = null;
    }

    @Override
    public void onClick(View v) {
        boolean eval = false;
        switch(v.getId()) {
            case R.id.button_0:
                opStack.push("0");
                break;
            case R.id.button_1:
                opStack.push("1");
                break;
            case R.id.button_2:
                opStack.push("2");
                break;
            case R.id.button_3:
                opStack.push("3");
                break;
            case R.id.button_4:
                opStack.push("4");
                break;
            case R.id.button_5:
                opStack.push("5");
                break;
            case R.id.button_6:
                opStack.push("6");
                break;
            case R.id.button_7:
                opStack.push("7");
                break;
            case R.id.button_8:
                opStack.push("8");
                break;
            case R.id.button_9:
                opStack.push("9");
                break;
            case R.id.button_comma:
                opStack.push(".");
                break;
            case R.id.button_plus:
                currOp = OPs.ADD;
                break;
            case R.id.button_minus:
                currOp = OPs.SUB;
                break;
            case R.id.button_mult:
                currOp = OPs.MULT;
                break;
            case R.id.button_div:
                currOp = OPs.DIV;
                break;
            case R.id.button_back:
                value.setLength(value.length()-1);
                break;
            case R.id.button_del:
                value.setLength(0);
                break;
            case R.id.button_equal:
                if(currOp == null) {
                    //ALERT
                    return;
                }
                eval = true;
                break;
        }
        this.setText(eval);

    }

    public void setText(boolean eval) {
        if(eval == true) {
            StringBuilder operand = new StringBuilder("");
            while(!opStack.isEmpty()) {
                if(opStack.peek() instanceof Enum) {
                    this.calculate(operand, (OPs) opStack.pop());
                    currOp = null;
                } else {
                    operand.append(opStack.pop());
                }
            }
        } else {
            value.append(opStack.pop());
        }
        view.setText(value.toString());
    }

    public void calculate(StringBuilder operand, OPs operation) {
        float result;
        switch(operation) {
            case ADD:
                result = (Float.parseFloat(value.toString()) + Float.parseFloat(operand.toString()));
                value.replace(0, value.length(), Float.toString(result));
                break;
            case SUB:
                result = (Float.parseFloat(value.toString()) - Float.parseFloat(operand.toString()));
                value.replace(0, value.length(), Float.toString(result));
                break;
            case MULT:
                result = (Float.parseFloat(value.toString()) * Float.parseFloat(operand.toString()));
                value.replace(0, value.length(), Float.toString(result));
                break;
            case DIV:
                result = (Float.parseFloat(value.toString())/Float.parseFloat(operand.toString()));
                value.replace(0, value.length(), Float.toString(result));
                break;
        }
    }
}
