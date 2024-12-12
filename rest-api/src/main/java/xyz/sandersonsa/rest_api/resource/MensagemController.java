package xyz.sandersonsa.rest_api.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.sandersonsa.rest_api.model.Mensagem;
import xyz.sandersonsa.rest_api.service.MensagemService;

@RestController
@RequestMapping("/api/v1")
public class MensagemController {

    private final MensagemService service;

    public MensagemController(MensagemService service) {
        this.service = service;
    }

    @PostMapping("/mensagem")
    public ResponseEntity<Mensagem> salvarMensagem(@RequestBody Mensagem mensagem) {
        Mensagem savedMensagem = service.salvarMensagem(mensagem);
        return ResponseEntity.ok(savedMensagem);
    }

    @PostMapping("/teste")
    public ResponseEntity<Void> perf() {
        return ResponseEntity.ok().build();
    }

}
