package com.example.myapplication.search.QParser;

import com.example.myapplication.search.PostObj;
import com.example.myapplication.search.StoreEngine;

import java.util.List;

public class Parser {
    // The tokenizer (class field) this parser will use.
    Tokenizer tokenizer;
    StoreEngine storeEngine;
    int numLBRA = 0;

    /**
     * Parser class constructor
     * Simply sets the tokenizer field.
     */
    public Parser(Tokenizer tokenizer, StoreEngine storeEngine) {
        this.tokenizer = tokenizer;
        this.storeEngine = storeEngine;
    }

    public List<PostObj> evaluate() {
        return this.parseExp().evaluate();
    }

    /**
     * for debug use
     */
    public void printTokenizer() {
        while (tokenizer.hasNext()) {
            System.out.println("TYPE " + tokenizer.current().getType());
            System.out.println("TOKEN " + tokenizer.current().getToken());
            tokenizer.next();
        }
    }

    /**
     * Adheres to the grammar rule:
     * <exp> ::= <term> | <term> AND <exp> | <term> OR <exp>
     *
     * @return type: Exp.
     */
    public Exp parseExp() {

        Exp term = parseTerm();

        if (tokenizer.hasNext()) {
            if (tokenizer.current().getType() == Token.Type.AND) {
                tokenizer.next();
                return new AndExp(term, parseExp());
            } else if (tokenizer.current().getType() == Token.Type.OR) {
                tokenizer.next();
                return new OrExp(term, parseExp());
            } else if (tokenizer.current().getType() == Token.Type.RBRA && numLBRA > 0) {
                return term;
            } else {
                throw new IllegalProductionException("");
            }
        } else {
            return term;
        }

    }

    /**
     * Adheres to the grammar rule:
     * <term>   ::=  <factor> | NOT <factor>
     *
     * @return type: Exp.
     */
    public Exp parseTerm() {
        if (tokenizer.hasNext()) {
            if (tokenizer.current().getType() == Token.Type.NOT) {
                tokenizer.next();
                return new NotExp(parseFactor(), this.storeEngine);
            } else {
                return parseFactor();
            }
        } else {
            throw new IllegalProductionException("Illegal Term");
        }
    }

    /**
     * Adheres to the grammar rule:
     * <factor> ::= <user> | <tag> | ( expression )
     *
     * @return type: Exp.
     */
    public Exp parseFactor() {

        if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.USER) {
            String user = tokenizer.current().getToken();
            tokenizer.next();
            return new UserExp(user, this.storeEngine);
        } else if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.TAG) {
            String tag = tokenizer.current().getToken();
            tokenizer.next();
            return new TagExp(tag, this.storeEngine);
        } else if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.LBRA) {
            numLBRA++;
            tokenizer.next();
            if (tokenizer.hasNext()) {
                Exp exp = parseExp();
                if (tokenizer.hasNext() && tokenizer.current().getType() == Token.Type.RBRA) {
                    numLBRA--;
                    tokenizer.next();
                    return exp;
                } else {
                    throw new IllegalProductionException("Illegal Factor");
                }
            } else {
                throw new IllegalProductionException("Illegal Factor");
            }
        } else {
            throw new IllegalProductionException("Illegal Factor");
        }

    }

    /**
     * The following exception should be thrown if the parse is faced with series of tokens that do not
     * correlate with any possible production rule.
     */
    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }
}
