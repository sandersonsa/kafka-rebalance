package xyz.sandersonsa.rest_api.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class TesteService {

    private AtomicInteger count;

    public TesteService() {
        count = new AtomicInteger(0);
    }

    public void teste() {
        log.info(" ### Teste - " + count.getAndIncrement() + "###");
    }

}