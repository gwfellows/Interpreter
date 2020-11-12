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
public enum TokenType {
    //one char
    PLUS,
    HYPHEN,
    STAR,
    SLASH,
    LPAREN,
    RPAREN,
    //datatypes
    NUMBER,
    STRING,
    //other
    NEWLINE,
    EOF
}
