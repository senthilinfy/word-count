package com.word.validate;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WordValidatorTest {
    private WordValidator wordValidator = new WordValidator();

    @Test
    public void testAddingValidWords() {
        assertThat(wordValidator.validate("test")).isTrue();
        assertThat(wordValidator.validate("blume")).isTrue();
    }

    @Test
    public void testAddingInValidWords() {
        assertThat(wordValidator.validate("tes ")).isFalse();
        assertThat(wordValidator.validate("123dfssfd")).isFalse();
        assertThat(wordValidator.validate("4343")).isFalse();
        assertThat(wordValidator.validate(null)).isFalse();
    }
}