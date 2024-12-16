package xyz.sandersonsa.kafka_sp.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.sandersonsa.kafka_sp.service.SoapService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IpvaBO {

    @Autowired
    SoapService soap;

    public List<String> callSoapParalelo(final String fixo, final String renavam) {
        HashMap<Integer, String> retorno = new HashMap<>();
        List<String> resultado = new ArrayList<String>();
        String format  = "%s%s";

        var starTime = System.currentTimeMillis();

        CompletableFuture<String> retorno01 = CompletableFuture.supplyAsync(() -> retorno.put(1, soap.callSoap(fixo, String.format(format, renavam, "01"))));
        CompletableFuture<String> retorno02 = CompletableFuture.supplyAsync(() -> retorno.put(2, soap.callSoap(fixo, String.format(format, renavam, "02"))));
        CompletableFuture<String> retorno03 = CompletableFuture.supplyAsync(() -> retorno.put(3, soap.callSoap(fixo, String.format(format, renavam, "03"))));
        CompletableFuture<String> retorno04 = CompletableFuture.supplyAsync(() -> retorno.put(4, soap.callSoap(fixo, String.format(format, renavam, "04"))));
        CompletableFuture<String> retorno05 = CompletableFuture.supplyAsync(() -> retorno.put(5, soap.callSoap(fixo, String.format(format, renavam, "05"))));
        CompletableFuture<String> retorno06 = CompletableFuture.supplyAsync(() -> retorno.put(6, soap.callSoap(fixo, String.format(format, renavam, "06"))));
        CompletableFuture<String> retorno07 = CompletableFuture.supplyAsync(() -> retorno.put(7, soap.callSoap(fixo, String.format(format, renavam, "07"))));
        CompletableFuture<String> retorno08 = CompletableFuture.supplyAsync(() -> retorno.put(8, soap.callSoap(fixo, String.format(format, renavam, "08"))));
        CompletableFuture<String> retorno09 = CompletableFuture.supplyAsync(() -> retorno.put(9, soap.callSoap(fixo, String.format(format, renavam, "09"))));
        CompletableFuture<String> retorno10 = CompletableFuture.supplyAsync(() -> retorno.put(10, soap.callSoap(fixo, String.format(format, renavam, "10"))));

        CompletableFuture.allOf(retorno01, retorno02, retorno03, retorno04, retorno05,
                                retorno06, retorno07, retorno08, retorno09, retorno10).join();
        
        for (Integer indice : retorno.keySet()) {
            var value = retorno.get(indice);
            if (value != null && !value.trim().equals("OK")) resultado.add(value);
        }

        var endTime = System.currentTimeMillis();
        log.info("Tempo total de todas as chamadas: {} mSec", (endTime - starTime));

        return resultado;
    }

    public String callSoapSerial(final String fixo, final String renavam) {

        String format  = "%s%s";

        // log.info("Iniciando chamadas às páginas da transacao 23 em série...");
        var starTime = System.currentTimeMillis();

        String retorno01 = soap.callSoap(fixo, String.format(format, renavam, "01"));
        String retorno02 = soap.callSoap(fixo, String.format(format, renavam, "02"));
        String retorno03 = soap.callSoap(fixo, String.format(format, renavam, "03"));
        String retorno04 = soap.callSoap(fixo, String.format(format, renavam, "04"));
        String retorno05 = soap.callSoap(fixo, String.format(format, renavam, "05"));
        String retorno06 = soap.callSoap(fixo, String.format(format, renavam, "06"));
        String retorno07 = soap.callSoap(fixo, String.format(format, renavam, "07"));
        String retorno08 = soap.callSoap(fixo, String.format(format, renavam, "08"));
        String retorno09 = soap.callSoap(fixo, String.format(format, renavam, "09"));
        String retorno10 = soap.callSoap(fixo, String.format(format, renavam, "10"));

        String retorno = String.format(
                "%s%s%s%s%s%s%s%s%s%s", 
                retorno01, retorno02, retorno03, retorno04, retorno05, 
                retorno06, retorno07, retorno08, retorno09, retorno10);

        var endTime = System.currentTimeMillis();
        log.info("Tempo total de todas as chamadas: {} mSec", (endTime - starTime));

        return retorno;
    }


}
