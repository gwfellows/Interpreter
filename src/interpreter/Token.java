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
public class Token {

    Object value;
    TokenType type;

    Token(TokenType type, Object value) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        if (value == null) {
            return String.format("%s", type);
        } else {
            return String.format("%s with value %s", type, value);
        }
    }

}
