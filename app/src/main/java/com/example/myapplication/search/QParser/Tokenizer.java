package com.example.myapplication.search.QParser;

/**
 * Welcome to Task 1.
 * In this task your job is to implement the next() method. Just some rules:
 * 1. You may NOT modify any other classes in this task 1 package.
 * 2. You may not modify any of the methods or fields (that already exist) within this class.
 * 3. You may create additional fields or methods to finish you implementation within this class.
 * <p>
 * Tokenization, within the context of this lab, is the process of splitting a string into
 * small units called, 'Tokens', to be passed onto the Parser.
 */
public class Tokenizer {
    private String buffer;          // String to be transformed into tokens each time next() is called.
    private Token currentToken;     // The current token. The next token is extracted when next() is called.

    /**
     * Tokenizer class constructor
     * The constructor extracts the first token and save it to currentToken
     * **** please do not modify this part ****
     */
    public Tokenizer(String text) {
        buffer = text;          // save input text (string)
        next();                 // extracts the first token.
    }

    /**
     * This function will find and extract a next token from {@code _buffer} and
     * save the token to {@code currentToken}.
     */
    public void next() {
        buffer = buffer.trim();     // remove whitespace

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        /*
        To help you, we have already written the first few steps in the tokenization process.
        The rest will follow a similar format.
         */
        char firstChar = buffer.charAt(0);
        if (firstChar == '&')
            currentToken = new Token("&", Token.Type.AND);
        if (firstChar == '|')
            currentToken = new Token("|", Token.Type.OR);
        if (firstChar == '!')
            currentToken = new Token("!", Token.Type.NOT);
        if (firstChar == '(')
            currentToken = new Token("(", Token.Type.LBRA);
        if (firstChar == ')')
            currentToken = new Token(")", Token.Type.RBRA);
        if (firstChar == '#') {
            int i = 1;
            while (i < buffer.length()
                    && (Character.isAlphabetic(buffer.charAt(i)) || Character.isDigit(buffer.charAt(i)))) {
                i++;
            }
            currentToken = new Token(buffer.substring(0, i), Token.Type.TAG);
        }
        if (firstChar == '@') {
            int i = 1;
            while (i < buffer.length()
                    && (Character.isAlphabetic(buffer.charAt(i)) || Character.isDigit(buffer.charAt(i)))) {
                i++;
            }
            currentToken = new Token(buffer.substring(0, i), Token.Type.USER);
        }
        if (firstChar != '&'
                && firstChar != '|'
                && firstChar != '!'
                && firstChar != '('
                && firstChar != ')'
                && firstChar != '@'
                && firstChar != '#')
            throw new Token.IllegalTokenException("IllegalToken");

        // Remove the extracted token from buffer
        int tokenLen = currentToken.getToken().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     * Returns the current token extracted by {@code next()}
     * **** please do not modify this part ****
     *
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Check whether there still exists another tokens in the buffer or not
     * **** please do not modify this part ****
     *
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }
}