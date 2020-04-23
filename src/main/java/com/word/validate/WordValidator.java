package com.word.validate;

public class WordValidator {

    public boolean validate(String word) {
        return word != null && word.matches("^[a-zA-Z]*$");
    }

}
