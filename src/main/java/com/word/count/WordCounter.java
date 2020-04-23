

package com.word.count;

import com.word.translator.Translator;
import com.word.validate.WordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordCounter {

    private static Logger logger = LoggerFactory.getLogger(WordCounter.class);

    private final Translator translator;
    private final CountKeeper countKeeper;
    private final WordValidator wordValidator;

    public WordCounter(final WordValidator wordValidator, final Translator translator, final CountKeeper countKeeper) {
        this.wordValidator = wordValidator;
        this.countKeeper = countKeeper;
        this.translator = translator;
    }

    public void add(String word) {
        if (!wordValidator.validate(word)) {
            logger.warn("Got invalid word {}. Only alphabets are allowed ", word);
            return;
        }
        countKeeper.add(translator.translate(word));
    }

    public Long getWordCount(String word) {
        return countKeeper.getCount(translator.translate(word));
    }
}
