package com.example.fixfilter;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author Evgeny Borisov
 */
@Service
@EnableScheduling
public class MyService {


    @Retryable(maxAttempts = 5,backoff = @Backoff(delay = 10),value = IllegalStateException.class)
    public void doWork(int x) {
        System.out.println(x);
        Random random = new Random();
        int i = random.nextInt(5);
        if (i == 2) {
            throw new IllegalStateException("bla");
        }
    }
}
