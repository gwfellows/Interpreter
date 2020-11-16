/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MathParser;

import java.util.Scanner;

/**
 *
 * @author gfellows
 */
//|--------------------------------|
//|SIMPLE MATH PARSER AND EVALUATOR|
//|--------------------------------|
//inspired by https://www.booleanworld.com/building-recursive-descent-parsers-definitive-guide/
public class MathParser {

    /**
     * @param args the command line arguments
     */
    //Main logic
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println(
                "This is a simple calculator\n"
                + "Enter expressions and they will be evaluated\n"
                + "Enter 'exit' to exit the program\n"
                + "Enter 'help' for more information\n"
        );
        while (true) {
            String nextLine = input.nextLine(); //the command/expression
            //exit on command exit
            if (nextLine.equals("exit")) {
                System.exit(0);
            }
            //show help on command help
            if (nextLine.equals("help")) {
                System.out.println("Supported operators:\n"
                        + " + for addition\n"
                        + " - for subtraction\n"
                        + " * for multiplication\n"
                        + " / for division (true division)\n"
                        + "Numbers can use + or - as a sign and may be integer or floating point\n"
                );
            } //otherwise evaluate the input and print the result (first checking if the input is valid)
            else {
                Parser code = new Parser(nextLine + " "); //make a parser for the input 
                char isValid = code.isValid(nextLine);
                //if it is valid...
                if (isValid == ' ') {
                    try {
                        System.out.println(code.expr() + "\n");
                    } catch (Exception e) {
                        System.out.println("invalid expression\n");
                    }
                    //if it is invalid...
                } else {
                    System.out.println("Invalid charecter '" + isValid + "'\n"
                            + "For a list of valid operators, enter 'help'\n");
                }
            }
        }
    }
}
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

class Parser { // a simple recursive-descent math parser

    String source;
    char current;
    int pos = 0;
    String ignore = "\t "; //these are whitespace charecters
    String valid = " \t-+/*.()0123456789"; //these are valid charecters

    // constructor
    Parser(String source) {
        this.source = source;
        this.current = source.charAt(pos);
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

    //is this a valid string?
    //if so, return ' '
    //if not, return the first invalid charecter
    char isValid(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (valid.indexOf(c) == -1) {
                return c;
            }
        }
        return ' ';
    }
}
