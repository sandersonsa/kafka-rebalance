package xyz.sandersonsa.rest_api.service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class TesteService {

    @Value("${app.thread.sleep}")
    private String THREAD_SLEEP;

    private AtomicInteger count;

    public TesteService() {
        count = new AtomicInteger(0);
    }

    public void testev2() {
        // try {
        //     Thread.sleep(Integer.parseInt(THREAD_SLEEP));
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        try {
            TimeUnit.MILLISECONDS.sleep(Integer.parseInt(THREAD_SLEEP));
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        log.info(" ### Teste V2 - " + count.getAndIncrement() + " ###");
    }

    public void teste(AtomicInteger count) {
        try {
            Thread.sleep(Integer.parseInt(THREAD_SLEEP));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(" ### Teste - " + count.getAndIncrement() + " ###");
    }

}
