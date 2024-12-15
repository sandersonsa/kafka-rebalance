package xyz.sandersonsa.kafka_sp.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import xyz.sandersonsa.kafka_sp.bo.IpvaBO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/")
@RestController
public class SoapController {
    
    private static final String FIXO = "0000340234           SFSFRJ1001100345";

    @Autowired
    private IpvaBO ipvaBO;

    @Value("${app.lista.renavam}")
    String[] listaRenavam;

    @GetMapping("/serial/{quantidade}")
    public String serial(@PathVariable Integer quantidade) {
        for (int i = 0; i < quantidade; i++) {
            ipvaBO.callSoapSerial(FIXO, getRenavam());
            log.info("chamada {} finalizada", i);
        }
        return "Chamada serial disparada...";
    }

    @GetMapping("/paralelo/{quantidade}")
    public String paralelo(@PathVariable Integer quantidade) {
        for (int i = 0; i < quantidade; i++) {
            ipvaBO.callSoapParalelo(FIXO, getRenavam());
            log.info("chamada {} finalizada", i);
        }
        return "Chamada paralela disparada...";
    }

    @GetMapping("/renavam/{renavam}")
    public List<String> renavam(@PathVariable String renavam) {
        return ipvaBO.callSoapParalelo(FIXO, renavam);
    }

    private String getRenavam() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(listaRenavam.length);
        String renavam = listaRenavam[randomIndex];
        log.info(">> {}", renavam);
        return renavam;
    }

}
