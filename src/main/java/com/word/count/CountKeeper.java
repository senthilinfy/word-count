package com.word.count;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class CountKeeper {
    private static Logger logger = LoggerFactory.getLogger(CountKeeper.class);
    private ConcurrentHashMap<String, LongAdder> words = new ConcurrentHashMap<>();

    public void add(String word) {
        if (word == null) {
            logger.warn("Can't add null value");
            return;
        }
        initializeKey(word);
        words.get(word).increment();
    }

    private void initializeKey(String word) {
        if(!words.containsKey(word)) {
            words.putIfAbsent(word, new LongAdder());
        }
    }

    public Long getCount(String word) {
        if (word == null || !words.containsKey(word)) {
            return 0L;
        }
        return words.get(word).sum();
    }

}
