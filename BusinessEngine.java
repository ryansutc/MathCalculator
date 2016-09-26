package com.example.w0143446.mathcalculator;

import android.widget.Switch;

/**
 * BusinessEngine Class handles all math, backend logic
 * Created by w0143446 on 9/23/2016.
 */
public class BusinessEngine {

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
        String delims = "[ ]+";
        String[] screenArray = str.split(delims);
        if (screenArray.length != 3){
            System.out.println("An error has occurred. The screen length is " + screenArray.length);
            return null;
        }
        else {
            return screenArray;
        }
    }

}
