package xyz.sandersonsa.kafkaproducer.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.sandersonsa.kafkaproducer.service.ProducerService;

import java.util.UUID;

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
        for (int i = 0; i < Integer.parseInt(quantidade); i++) {
            UUID uuid = UUID.randomUUID();
            producerService.sendMessages(uuid.toString());
        }
        System.out.println(quantidade + " mensagens enviadas");
        return "OK";
    }

    @GetMapping("/detran/{transacao}/{quantidade}")
    public String producerMessagesDetran(@PathVariable String transacao, @PathVariable String quantidade) throws NumberFormatException, InterruptedException {
        System.out.println("Mensagem " + transacao + "...");
        for (int i = 0; i < Integer.parseInt(quantidade); i++) {
            UUID uuid = UUID.randomUUID();
            switch (transacao) {
                case "23":
                    producerService.sendMessagesJson023(uuid.toString());
                    break;
                case "17":
                    producerService.sendMessagesJson017(uuid.toString());
                    break;
                default:
                    System.out.println(">>> Transação " + transacao + " desconhecida!!!");
                    break;
            }
        }
        System.out.println(quantidade + " mensagens enviadas");
        return "OK-DETRAN";
    }

}
