package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.example.myapplication.search.QParser.Token;
import com.example.myapplication.search.QParser.Tokenizer;

import org.junit.Test;
import org.junit.runners.Parameterized;

public class TokenizerTest {

    /*
        We suggest you test your tokenizer by also running it in your own terminal and checking the output.

        We advise you write additional tests to increase the confidence of your implementation. Simply getting these
	    tests correct does not mean your solution is robust enough pass the marking tests.
     */

    private static Tokenizer tokenizer;
    private static final String MID = "@a | #b & #c";
    private static final String ADVANCED = "(@a & @b | #c) & #d | @e";
    private static final String AND_OR = "&|";

    @Test(timeout=1000)
    public void testAddToken() {
    	tokenizer = new Tokenizer(AND_OR);

    	// check the type of the first token
        assertEquals("wrong token type", Token.Type.AND, tokenizer.current().getType());

        // check the actual token value"
        assertEquals("wrong token value", "&", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testSubToken() {
    	tokenizer = new Tokenizer(AND_OR);

        // extract next token (just to skip first passCase token)
        tokenizer.next();

        // check the type of the first token
        assertEquals("wrong token type", Token.Type.OR, tokenizer.current().getType());

        // check the actual token value
        assertEquals("wrong token value", "|", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testFirstToken(){
    	tokenizer = new Tokenizer(ADVANCED);
    	
    	// check the type of the first token
        assertEquals("wrong token type", Token.Type.LBRA, tokenizer.current().getType());
        // check the actual token value
        assertEquals("wrong token value", "(", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testMidTokenResult() {
        tokenizer = new Tokenizer(MID);

        assertEquals(new Token("@a", Token.Type.USER), tokenizer.current());

        // test second token *
        tokenizer.next();
        assertEquals(Token.Type.OR, tokenizer.current().getType());

        // test third token INT(5)
        tokenizer.next();
        assertEquals(new Token("#b", Token.Type.TAG), tokenizer.current());

        // test forth token -
        tokenizer.next();
        assertEquals(Token.Type.AND, tokenizer.current().getType());

        // test fifth token INT(3)
        tokenizer.next();
        assertEquals(new Token("#c", Token.Type.TAG), tokenizer.current());
    }

    @Test(timeout=1000)
    public void testAdvancedTokenResult(){
    	tokenizer = new Tokenizer(ADVANCED);

        // test first token (
        assertEquals(Token.Type.LBRA, tokenizer.current().getType());

        // test second token INT(100)
        tokenizer.next();
        assertEquals(new Token("@a", Token.Type.USER), tokenizer.current());

        // test third token +
        tokenizer.next();
        assertEquals(new Token("&", Token.Type.AND), tokenizer.current());

        // test fourth token INT(2)
        tokenizer.next();
        assertEquals(new Token("@b", Token.Type.USER), tokenizer.current());

        // test fourth token -
        tokenizer.next();
        assertEquals(new Token("|", Token.Type.OR), tokenizer.current());

        // test fifth token INT(40)
        tokenizer.next();
        assertEquals(new Token("#c", Token.Type.TAG), tokenizer.current());

        // test sixth token )
        tokenizer.next();
        assertEquals(new Token(")", Token.Type.RBRA), tokenizer.current());

        // test seventh token /
        tokenizer.next();
        assertEquals(new Token("&", Token.Type.AND), tokenizer.current());

        // test eighth token INT(10)
        tokenizer.next();
        assertEquals(new Token("#d", Token.Type.TAG), tokenizer.current());

        // test ninth token *
        tokenizer.next();
        assertEquals(new Token("|", Token.Type.OR), tokenizer.current());

        // test tenth token INT(4789)
        tokenizer.next();
        assertEquals(new Token("@e", Token.Type.USER), tokenizer.current());
    }

    @Test(timeout=1000)
    public void testExceptionToken() {
        // Test a series of non-identifiable tokens
        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("Banana and Apples");
            tokenizer.next();
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("(1+2.5)/2");
            int i = -1;
            while (i++ < "(1+2.5)/2".length()) {
                tokenizer.next();
            }
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("(1+A)/2");
            int i = -1;
            while (i++ < "(1+A)/2".length()) {
                System.out.println(i);
                tokenizer.next();
            }
        });

        assertThrows(Token.IllegalTokenException.class, () -> {
            tokenizer = new Tokenizer("(2 + 8)&#8704");
            int i = -1;
            while (i++ < "(2 + 8)&#8704".length()) {
                System.out.println(i);
                tokenizer.next();
            }
        });
    }
}
