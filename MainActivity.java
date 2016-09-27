package com.example.w0143446.mathcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.*;
import android.text.TextWatcher;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnEquals;
    Button btnClear;
    Button btnRemove;
    Button btnNegative;
    Button btnMultiply;
    Button btnDivide;
    Button btnSubtract;
    Button btnDecimal;
    Button btnAdd;
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    EditText edittextScreen;
    //An array of the screen to 3 parts:
    //[Left number], [operator], [right number]
    private String[] screenArray;
    private String screenText;
    private BusinessEngine be = new BusinessEngine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEquals = (Button) findViewById(R.id.btnEquals);
        btnClear= (Button) findViewById(R.id.btnClear);
        btnRemove = (Button) findViewById(R.id.btnRemove);
        btnNegative = (Button) findViewById(R.id.btnNegative);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnSubtract = (Button) findViewById(R.id.btnSubtract);
        btnDecimal = (Button) findViewById(R.id.btnDecimal);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        edittextScreen = (EditText) findViewById(R.id.edittextScreen);
        edittextScreen.addTextChangedListener(screenTW);
        screenText = edittextScreen.getText().toString();

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDecimal.setOnClickListener(this);

        btnAdd.setOnClickListener(oclOperators);
        btnSubtract.setOnClickListener(oclOperators);
        btnDivide.setOnClickListener(oclOperators);
        btnMultiply.setOnClickListener(oclOperators);
        btnClear.setOnClickListener(oclOperators);
        btnRemove.setOnClickListener(oclOperators);
        btnEquals.setOnClickListener(oclOperators);
        btnNegative.setOnClickListener(oclOperators);

        this.triggerButtons();
    }

    @Override
    //handle onClick events for NUMERIC buttons in this activity
    public void onClick(View v)
    {
        // 1) Possibly do explicit check for instance of first
        screenText = edittextScreen.getText().toString();
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        edittextScreen.setText(edittextScreen.getText() + buttonText);
    }

    //create special listener for operator buttons (*/-+)
    View.OnClickListener oclOperators = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String buttonText = b.getText().toString().toUpperCase();
            System.out.println("An operator button was pressed: " + buttonText);

            //Special Functions handling.
            //= Equals
            if (buttonText.equals("=")){
                //convert text to L O R array to calculate
                screenArray = be.getScreenArray(screenText);
                screenText = String.valueOf(be.calculate(screenArray));
                edittextScreen.setText(screenText);
                return;
            }
            //CLEAR
            else if (buttonText.equals("CLEAR")){
                screenText = "";
                edittextScreen.setText(screenText);
                return;
            }
            //backspace
            else if (buttonText.equals("<-")){
                if (screenText.endsWith(" ")){
                    screenText = be.removeChar(screenText);
                    screenText = be.removeChar(screenText);
                    screenText = be.removeChar(screenText);
                }
                else {
                    screenText = be.removeChar(screenText);
                }
                edittextScreen.setText(screenText);
                return;
            }

            //first check if it should ROLL with a calc
            if (be.canCalculate(screenText)){
                screenArray = be.getScreenArray(screenText);
                screenText = String.valueOf(be.calculate(screenArray));
            }

            //then run operators
           if (buttonText.equals("+")){
                screenText = screenText + " + ";
            }
            else if (buttonText.equals("-")){
                screenText = screenText + " - ";
            }
            else if (buttonText.equals("/")){
                screenText = screenText + " / ";
            }
            else if (buttonText.equals("*")){
                screenText = screenText + " * ";
            }
            else if (buttonText.equals("+/-")){
                screenText = be.calcNegative(screenText);
            }
            else {
                System.out.println("Could not find button");
            }
            edittextScreen.setText(screenText);
        }
    };

    //Control enabling/ disabling operator buttons
    private void activateOperators(boolean blnActivate){
        if (blnActivate){
            btnMultiply.setEnabled(true);
            btnDivide.setEnabled(true);
            btnSubtract.setEnabled(true);
            btnDecimal.setEnabled(true);
            btnAdd.setEnabled(true);
            btnNegative.setEnabled(true);
        }
        else {
            btnMultiply.setEnabled(false);
            btnDivide.setEnabled(false);
            btnSubtract.setEnabled(false);
            btnDecimal.setEnabled(false);
            btnAdd.setEnabled(false);
            btnNegative.setEnabled(false);
        }
    }

    //Toggle Buttons on and off based on textChange
    //this is the main point of validation
    private void triggerButtons(){

        // Enable/Disable =
        if (be.canCalculate(screenText)){
            btnEquals.setEnabled(true);
        }
        else {
            btnEquals.setEnabled(false);
        }

        //Enable/Disable Operators (+-/*)
        if (be.endswithOperator(screenText)){
            System.out.println("Operator Detected, deactivate");
            activateOperators(false);
        }
        else {
            activateOperators(true);
        }
        //enable disable <- / CLEAR
        if (screenText.equals("")){
            btnRemove.setEnabled(false);
            btnClear.setEnabled(false);
            activateOperators(false);
        }
        else  {
            btnRemove.setEnabled(true);
            btnClear.setEnabled(true);
        }

        //enable disable decimal .
        if (be.hasDecimal(screenText) && be.endswithNum(screenText) == false){
            btnDecimal.setEnabled(false);
        }
        else {
            btnDecimal.setEnabled(true);
        }

    }

    private TextWatcher screenTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("After Change Occurred");
            screenText = s.toString();
            if (screenText.equals(".")){
                screenText = "0.";
            }
            triggerButtons();
        }
    };
}//end class
