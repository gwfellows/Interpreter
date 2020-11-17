/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// |-----------------------------------------------------|
// |   EBNF Syntax for simple math expressions           |
// |                                                     |
// |   expr    =  term{("+"|"-") term}                   |
// |   term    =  factor{("*"|"/") factor}               |
// |   factor  =  "(" expr ")" | number                  |
// |   number  =  ("+"|"-") (digit)+ ["."] {digit}       |
// |   digit   =  "1"|"2"|"3"|"4"|"5"|"6"|"7"|"9"|"0"    |
// |-----------------------------------------------------|
// I know it is not good style to put multiple classes in 1 file,
// but since the main class is so small, I thought it was fine
package MathParser;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author gfellows
 */
public class Parser {

    String source;
    char current;
    int pos;
    static String ignore = "\t ";//these are whitespace charecters
    static String valid = " \t-+/*.()0123456789=abcdefghijklmnopqrstuvwxyz_ABCDEFGHIJKLMNOPQRSTUVWXYZ";//these are valid charecters
    HashMap<String, Double> vars = new HashMap<>();

    //empty constructor
    Parser() {
    }

    //read a line
    double readLine(String s) {
        //swap in constants
        //this generates a regex to find where a constant name appears, preceded and followed by non-constant charecters
        //this is to avoid swapping constants into the names of other constants
        //for example: ab=10 does not turn into 1.01.0=10 if a=1.0 and b=1.0
        String all;
        all = "";
        for (String i : vars.keySet()) {
            all += i + "|";
        }
        try {
            all = all.substring(0, all.length() - 1);
        } catch (Exception e) {
        }
        for (String i : vars.keySet()) {
            Pattern p = Pattern.compile(String.format("(?<=[^%s])%s(?=[^%s])", all, i, all));
            s = p.matcher(s).replaceAll(vars.get(i).toString());
        }
        this.pos = 0;
        this.source = s;
        this.current = s.charAt(pos);
        Double res;
        //if the source has an equal sign...
        if (source.contains("=")) {
            String right = source.substring(source.indexOf("=") + 1).trim() + " ";
            String left = source.substring(0, source.indexOf("=")).trim();
            //is the left side a valid identifier
            char[] charArray = left.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char ch = charArray[i];
                if (!(ch >= 'a' && ch <= 'z')) {
                    throw new RuntimeException(String.format("Cannot assign %s to %s", left, right));
                }
            }
            source = right;
            current = source.charAt(pos);
            //then parse the right side as an expression, putting the result in the map
            res = expr();
            vars.put(left, res);
        } else {
            res = expr();
        }
        //reset
        this.pos = -1;
        advance();
        //return a value
        return res;
    }

    //|---------------|
    //|GRAMMAR METHODS|
    //|---------------|
    double expr() {
        double val = term();
        while (match('+', '-')) {
            char op = previous();
            if (op == '+') {
                val += term();
            } else {
                val -= term();
            }
        }
        return val;
    }

    double term() {
        double val = factor();
        while (match('*', '/')) {
            char op = previous();
            if (op == '*') {
                val *= factor();
            } else {
                val /= factor();
            }
        }

        return val;
    }

    double factor() {
        if (match('(')) {
            double expr = expr();
            match(')');
            return expr;
        } else {
            double val = number();
            return val;
        }
    }

    double number() {
        String str = "";
        if (match('+', '-')) {
            char sign = previous();
            str += sign;
        }
        str += current;
        if (isAtEnd()) {
            return Double.parseDouble(str);
        } else {
            advance();
        }
        while (match('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')) {
            char digit = previous();
            str += digit;
        }
        if (match('.')) {
            char dot = previous();
            str += dot;
            while (match('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')) {
                char digit = previous();
                str += digit;
            }
        }
        return Double.parseDouble(str);
    }

    //|---------------|
    //|UTILITY METHODS|
    //|---------------|
    //advances parser by one char
    private void advance() {
        pos++;
        current = source.charAt(pos);
        if (valid.indexOf(current) == -1) {
            throw new RuntimeException(String.format("%s is not a valid charecter", current));
        }
    }

    //skip the whitespace
    private void skipWhitespace() {
        while (isAtEnd() == false && ignore.indexOf(current) != -1) {
            advance();
        }
    }

    //tries to match rules to the source
    private boolean match(char... symbols) {
        skipWhitespace();
        for (char symbol : symbols) {
            if (check(symbol)) {
                advance();
                return true;
            }
        }
        return false;
    }

    //matches rules
    private boolean check(char symbol) {
        if (isAtEnd()) {
            return false;
        }
        return current == symbol;
    }

    //is the parser at the end of the source?
    private boolean isAtEnd() {
        return (pos >= source.length() - 1);
    }

    //get the previous char
    private char previous() {
        int mypos = pos;
        char mycurrent = current;
        do {
            mypos--;
            mycurrent = source.charAt(mypos);
        } while (isAtEnd() == false && ignore.indexOf(mycurrent) != -1);
        return mycurrent;
    }

}
