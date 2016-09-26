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
    String[] screenArray;
    BusinessEngine be = new BusinessEngine();

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

        //edittextScreen.addTextChangedListener(screenTW);

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

        btnAdd.setOnClickListener(oclOperators);
        btnSubtract.setOnClickListener(oclOperators);
        btnDivide.setOnClickListener(oclOperators);
        btnMultiply.setOnClickListener(oclOperators);
        btnClear.setOnClickListener(oclOperators);
        btnRemove.setOnClickListener(oclOperators);
    }

    @Override
    //handle onClick events for numeric buttons in this activity
    public void onClick(View v)
    {
        // 1) Possibly do explicit check for instance of first
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        edittextScreen.setText(edittextScreen.getText() + buttonText);
        //try to convert to number. if fail it is operator.
    }

    //create special listener for operator buttons (*/-+)
    View.OnClickListener oclOperators = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("An operator button was pressed");
            Button b = (Button) v;
            String buttonText = b.getText().toString().toUpperCase();
            System.out.println(buttonText);
            String screenText = edittextScreen.getText().toString();

            //commence long if/then statement
            if (buttonText.equals("=")){
                //convert text to L O R array to calculate
                System.out.println("= was pressed");
                screenArray = be.getScreenArray(buttonText);
                if (screenArray != null) {
                   screenText = String.valueOf(be.calculate(screenArray));
                    activateOperators(true);
                }
            }
            else if (buttonText.equals("CLEAR")){
                screenText = "";
                System.out.println("CLEAR button pressed");
                activateOperators(false);
            }
            //backspace
            else if (buttonText.equals("<-")){
                screenText = be.removeChar(screenText);
                String pattern = "[+'-'*/]";
                if (screenText.matches(pattern)){
                    activateOperators(false);
                }
                else { activateOperators(true);}
            }
            //operators
            else if (buttonText.equals("+")){
                screenText = screenText + " + ";
                activateOperators(false);
            }
            else if (buttonText.equals("-")){
                screenText = screenText + " - ";
                activateOperators(false);
            }
            else if (buttonText.equals("/")){
                screenText = screenText + " / ";
                activateOperators(false);
            }
            else if (buttonText.equals("*")){
                screenText = screenText + " * ";
                activateOperators(false);
            }
            else {
                screenText = screenText;
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
        }
        else {
            btnMultiply.setEnabled(false);
            btnDivide.setEnabled(false);
            btnSubtract.setEnabled(false);
            btnDecimal.setEnabled(false);
            btnAdd.setEnabled(false);
        }
    }
/*
    private TextWatcher screenTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                //float bill = Float.parseFloat(s.toString());
                //updateForm(bill);
                System.out.println(s);
            } catch (NumberFormatException e) {

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("After Change Occurred");
        }
    };
*/
}
