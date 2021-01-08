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
        Parser code = new Parser(); //make a parser
        Scanner input = new Scanner(System.in);
        System.out.println(
                "|-----------------------------------|\n"
                + "|This is a simple calculator        |\n"
                + "|Enter expressions and assignments  |\n"
                + "|Enter 'exit' to exit the program   |\n"
                + "|Enter 'help' for more information  |\n"
                + "|Enter 'reset' to reset the program |\n"
                + "|-----------------------------------|"
        );
        while (true) {
            System.out.print(">>>");
            String nextLine = " " + input.nextLine() + " "; //the command/expression
            switch (nextLine) {
                //do nothing with an empty input
                case "  ":
                    break;
                //exit on command exit
                case " exit ":
                    System.exit(0);
                //reset on command reset
                case " reset ":
                    main(new String[0]);
                    break;
                //show help on command help
                case " help ":
                    System.out.println("Supported operators:\n"
                            + " + for addition\n"
                            + " - for subtraction\n"
                            + " * for multiplication\n"
                            + " / for division (true division)\n"
                            + " = for assignment of constants\n"
                            + "Constant names may only use the letters A-Z\n"
                            + "Numbers can use + or - as a sign and may be integer or floating point"
                    );
                    break;
                //otherwise evaluate the input and print the result (first checking if the input is valid)
                default:
                    try {
                    System.out.println(code.readLine(nextLine));
                } catch (Exception e) {
                    //catch and print any errors
                    System.out.println(e);
                }
                break;
            }
        }
    }
}
