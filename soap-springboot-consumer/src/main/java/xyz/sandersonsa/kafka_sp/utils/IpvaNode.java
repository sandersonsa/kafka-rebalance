package xyz.sandersonsa.kafka_sp.utils;

public enum IpvaNode {
    
    PARTE_FIXA("PARTE-FIXA"),
    PARTE_VARIAVEL("PARTE-VARIAVEL"),
    STRING("string");

    private String value;

    IpvaNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
