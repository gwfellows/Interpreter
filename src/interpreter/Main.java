/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author gfellows
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Parser code = new Parser(
                "1+1+2*(1/4)+5+9*7"
        );
        System.out.println(code.expr());
    }

}
