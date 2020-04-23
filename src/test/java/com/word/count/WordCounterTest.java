package com.word.count;


import com.word.translator.Translator;
import com.word.validate.WordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WordCounterTest {

    @Mock private Translator translator;
    private WordCounter wordCounter;

    @BeforeEach
    public void setUp() {
        wordCounter = new WordCounter(new WordValidator(), translator, new CountKeeper());
        when(translator.translate(anyString())).then(returnsFirstArg());
    }

    @Test
    public void addWord() {
        wordCounter.add("test");
        wordCounter.add("test");
        wordCounter.add("test");
        wordCounter.add("testing");
        assertThat(wordCounter.getWordCount("test")).isEqualTo(3);
        assertThat(wordCounter.getWordCount("testing")).isEqualTo(1);
    }

    @Test
    public void addInValidWords() {
        wordCounter.add("test123");
        wordCounter.add(null);
        assertThat(wordCounter.getWordCount("test")).isEqualTo(0);
    }
}