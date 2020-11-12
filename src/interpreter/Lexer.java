/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gfellows
 */
public class Lexer {

    static List<Token> scanTokens(String source) {

        List<Token> tokens = new ArrayList<>();
        char prev = ' ';
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);

            if (Character.isDigit(c)) {
                if (Character.isDigit(prev)) {
                    double last = (double) tokens.get(tokens.size() - 1).value;
                    tokens.get(tokens.size() - 1).value = last * 10 + Double.parseDouble(Character.toString(c));
                } else {
                    addToken(tokens, TokenType.NUMBER, c);
                }
            }

            switch (c) {
                case '+' ->
                    addToken(tokens, TokenType.PLUS, c);
                case '-' ->
                    addToken(tokens, TokenType.HYPHEN, c);
                case '*' ->
                    addToken(tokens, TokenType.STAR, c);
                case '/' ->
                    addToken(tokens, TokenType.SLASH, c);
                case '(' ->
                    addToken(tokens, TokenType.LPAREN, c);
                case ')' ->
                    addToken(tokens, TokenType.RPAREN, c);
            }

            prev = c;
        }
        addToken(tokens, TokenType.EOF, ' ');

        return tokens;
    }

    static void addToken(List<Token> tokens, TokenType type, char c) {
        if (type == TokenType.NUMBER) {
            tokens.add(new Token(type, Double.parseDouble(Character.toString(c))));
        } else {
            tokens.add(new Token(type, null));
        }
    }

}
