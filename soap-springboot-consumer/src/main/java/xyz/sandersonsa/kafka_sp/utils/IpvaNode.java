package xyz.sandersonsa.kafka_sp.utils;

public enum IpvaNode {
    
    PARTE_FIXA("PARTE-FIXA"),
    PARTE_VARIAVEL("PARTE-VARIAVEL"),
    STRING("string"),
    CAUSED_BY("CausedBy"),
    ERROR_TEXT("ErrorText");

    private String value;

    IpvaNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
