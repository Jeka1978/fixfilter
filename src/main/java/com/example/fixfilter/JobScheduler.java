package com.example.fixfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Evgeny Borisov
 */
@Component
@EnableScheduling
public class JobScheduler {

    @Autowired
    private MyService myService;

    private int x;

    @Scheduled(fixedDelay = 500)
    public void doX() {
        myService.doWork(x++);
    }
}
