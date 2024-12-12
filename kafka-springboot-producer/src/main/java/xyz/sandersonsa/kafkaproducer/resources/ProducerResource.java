package xyz.sandersonsa.kafkaproducer.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.sandersonsa.kafkaproducer.service.ProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/producer")
public class ProducerResource {

    private final ProducerService producerService;

    public ProducerResource(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/{quantidade}")
    public String producerMessages(@PathVariable String quantidade) throws NumberFormatException, InterruptedException {
        producerService.sendMessages(Integer.parseInt(quantidade));
        return "OK";
    }

    @GetMapping("/detran/{quantidade}")
    public String producerMessagesDetran(@PathVariable String quantidade) throws NumberFormatException, InterruptedException {
        producerService.sendMessagesJson(Integer.parseInt(quantidade));
        return "OK-DETRAN";
    }
    

}
