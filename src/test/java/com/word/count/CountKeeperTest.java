package com.word.count;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class CountKeeperTest {
    public static final int THREAD_COUNT = 10;
    public static final int WORDS_PER_THREAD = 10000;

    private static Logger logger = LoggerFactory.getLogger(CountKeeperTest.class);
    private CountKeeper countKeeper;

    @BeforeEach
    public void setUp() {
        countKeeper = new CountKeeper();
    }

    @Test
    public void addWord() {
        countKeeper.add("test");
        countKeeper.add("test");
        countKeeper.add("test");
        countKeeper.add("testing");
        assertThat(countKeeper.getCount("test")).isEqualTo(3);
        assertThat(countKeeper.getCount("testing")).isEqualTo(1);
    }

    @Test
    public void addNullWord() {
        countKeeper.add(null);
        assertThat(countKeeper.getCount("test")).isEqualTo(0);
    }

    @Test
    public void addWordsFromMultipleThreads() throws InterruptedException {
        ExecutorService workerThreadPool
                = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        Instant start = Instant.now();
        IntStream.range(0, THREAD_COUNT).forEach(t ->
                workerThreadPool.submit(createAddThread(latch, "test", WORDS_PER_THREAD)));
        latch.await();
        long timeElapsed = Duration.between(start, Instant.now()).toMillis();
        logger.info("Time taken={}ms, threads={}, words from each word={}", timeElapsed,
                THREAD_COUNT, WORDS_PER_THREAD);
        // 10 threads * 100 times A in each thread
        assertThat(countKeeper.getCount("testA")).isEqualTo(1000);
    }

    private Runnable createAddThread(CountDownLatch latch, String word, int times) {
        return () -> {
            IntStream.range(0, times).forEach(counter -> countKeeper.add(getWord(word, counter)));
            latch.countDown();
        };
    }

    private String getWord(String word, int counter) {
        StringBuilder sb = new StringBuilder(word);
        sb.append((char) (counter % 100));
        return sb.toString();
    }
}