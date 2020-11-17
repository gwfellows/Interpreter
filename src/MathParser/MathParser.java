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
        Parser code = new Parser(); //make a parser for the input 
        Scanner input = new Scanner(System.in);
        System.out.println(
                "|-----------------------------------|\n"
                + "|This is a simple calculator        |\n"
                + "|Enter expressions and assignments  |\n"
                + "|Enter 'exit' to exit the program   |\n"
                + "|Enter 'help' for more information  |\n"
                + "|-----------------------------------|"
        );
        while (true) {
            System.out.print(">>>");
            String nextLine = " " + input.nextLine() + " "; //the command/expression
            switch (nextLine) {
            //exit on command exit
                case "  ":
                    break;
            //reset on command reset
                case " exit ":
                    System.exit(0);
            //show help on command help
                case " reset ":
                    main(new String[0]);
                    break;
            //otherwise evaluate the input and print the result (first checking if the input is valid)
                case " help ":
                    System.out.println("Supported operators:\n"
                            + " + for addition\n"
                            + " - for subtraction\n"
                            + " * for multiplication\n"
                            + " / for division (true division)\n"
                            + " = for assignment of constants\n"
                            + "Numbers can use + or - as a sign and may be integer or floating point"
                    );  break;
                default:
                    try {
                        System.out.println(code.readLine(nextLine));
                    } catch (Exception e) {
                        System.out.println(e);
                    }   break;
            }
        }
    }
}
