/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.util.List;

/**
 *
 * @author gfellows
 */
//this tutorial was helpful:
//https://www.booleanworld.com/building-recursive-descent-parsers-definitive-guide/
public class Parser {

    String source;
    List<Token> tokens;
    Token token;
    int pos = 0;

    Parser(String source) { //constructor
        this.source = source;
        this.tokens = Lexer.scanTokens(source);
        this.token = tokens.get(pos);
    }

    Token advance() { //advances parser by one token, and returns the current token for convenience
        pos++;
        token = tokens.get(pos);
        return token;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return false;
        }
        return peek().type == type;
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token previous() {
        return tokens.get(pos - 1);
    }

//    simple math exressions
//
//    expr   : term((PLUS|MINUS) term)*
//    term   : factor((MUL|DIV) factor)*
//    factor : LPAREN EXPRESSION RPAREN|NUMBER
    double expr() {
        System.out.println("Entering EXPR");
        System.out.println("Current Token: " + token);
        double val = term();
        System.out.println("Current Token: " + token);
        while (match(TokenType.PLUS, TokenType.HYPHEN)) {
            Token op = previous();
            if (op.type == TokenType.PLUS) {
                val += term();
            } else {
                val -= term();
            }
        }
        return val;
    }

    double term() {
        System.out.println("Entering TERM");
        System.out.println("Current Token: " + token);
        double val = factor();
        while (match(TokenType.STAR, TokenType.SLASH)) {
            Token op = previous();
            if (op.type == TokenType.STAR) {
                val *= factor();
            } else {
                val /= factor();
            }
        }

        System.out.println("EXR SAYS: " + val);
        System.out.println("NOW TKN IS ::" + peek() + " deal with it");
        return val;
    }

    double factor() {
        System.out.println("Entering FACTOR");
        System.out.println("Current Token: " + token);
        if (match(TokenType.LPAREN)) {
            double expr = expr();
            advance();
            return expr;
        } else {
            double val = (double) token.value;
            advance();
            return val;
        }
    }

}
