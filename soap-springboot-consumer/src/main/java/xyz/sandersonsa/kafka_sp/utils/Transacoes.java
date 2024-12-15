package xyz.sandersonsa.kafka_sp.utils;

public enum Transacoes {

    TRANSACAO_13(13),
    TRANSACAO_16(16),
    TRANSACAO_17(17),
    TRANSACAO_19(19),
    TRANSACAO_22(22),
    TRANSACAO_23(23),
    TRANSACAO_26(26);

    private final int transacao;

    Transacoes(int transacao) {
        this.transacao = transacao;
    }

    public int getTransacao() {
        return transacao;
    }

    public static boolean contem(int transacao){
        for(Transacoes transacaoEnum : Transacoes.values()){
            if(transacaoEnum.getTransacao() == transacao){
                return true;
            }
        }
        return false;
    }

}