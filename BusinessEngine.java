package com.example.w0143446.mathcalculator;

import android.widget.Switch;

import java.util.regex.Pattern;

/**
 * BusinessEngine Class handles all math, backend logic
 * Created by w0143446 on 9/23/2016.
 */
public class BusinessEngine {
    //runs the = calculation OR calculation before new operator if L O R
    public double calculate(String[] screen){
        Double Lnum = Double.parseDouble(screen[0]);
        String Operator = screen[1];
        Double Rnum = Double.parseDouble(screen[2]);

        switch (Operator) {
            case "+": return Lnum + Rnum;
            case "-": return Lnum - Rnum;
            case "/": return Lnum / Rnum;
            case "*": return Lnum * Rnum;
            default: return 9999; //workaround for errors
        }
    }

    //a simple function to remove the last character
    //for backspace button
    public String removeChar(String str){
        str = str.substring(0, str.length()-1);
        return str;
    }

    //a function to parse raw string to 3 part array
    //of Left Number Operator Right Number
    public String[] getScreenArray(String str){
        System.out.println(str);
        String delims = "[ ]";
        String[] screenArray = str.split(delims);

        return screenArray;

    }

    //last val is number check. Used for +/- & decimal
    public boolean endswithNum(String screenText){
        String n = "[0-9]$";
        if (screenText.matches(n)){
            System.out.println("Yes the text ends with a number");
            return true;
        }
        else {
            System.out.println("No the output does not end with a number");
            return false;
        }
    }

    //a function that tests the screen string to see if it can be calculated (ie. valid L O R).
    //this is called by the screen text changed listener as well as buttons that need to do calcs
    public boolean canCalculate(String screenText){
        String delims = "[ ]";
        String[] screenArray = screenText.split(delims);
        if (screenArray.length != 3) {
            System.out.println("An error has occurred. The screen length is " + screenArray.length);
            for (int x = 0; x < screenArray.length; x++) {
                System.out.println("ScreenArray " + x + screenArray[x]);
            }
            return false;
        }
        else {
            return true;
        }
    }
    //check if the screen includes an operator
    public boolean hasOperator(String screenText){
        String pattern = " "; //edit. workaround. bc all operators cause whitespace..
        if (screenText.contains(pattern)){
            return true;
        }
        else {
            return false;
        }
    }

    //check whether screen ends with operator
    public boolean endswithOperator(String screenText){
        String pattern = " "; //edit. workaround.
        if (screenText.endsWith(pattern)){
            return true;
        }
        else {
            return false;
        }
    }

    //check if screen includes decimal
    public boolean hasDecimal(String screenText){

        if (screenText.indexOf(".") != -1){
            return true;
        }
        else {
            return false;
        }
    }
    //convert a positive number negative, or vice versa
    //includes error handling that takes and returns screen directly
    public String calcNegative(String screenText){
        //check if screenText is a number of has an operator
        String newScreenText = "";
        double newNum;
        if (hasOperator(screenText)){
            //if it has an operator make sure it doesn't end with it!
            if (canCalculate(screenText)) {
                String[] screenArray = getScreenArray(screenText);

                for (int i=0;i<screenArray.length;i++){
                    if (i == (screenArray.length - 1)) { //if last value, make negative
                        newNum = Double.parseDouble(screenArray[i]);
                        newNum = newNum - (newNum * 2);
                        screenArray[i] = String.valueOf(newNum);
                    }
                    newScreenText += screenArray[i] + " ";
                }
                newScreenText.substring(0, newScreenText.length()-1);
            }
            return newScreenText;
        }
        else {
            newNum = Double.parseDouble(screenText);
            newNum = newNum - (newNum * 2);
            newScreenText += String.valueOf(newNum);
            return newScreenText;
        }
    }

}
