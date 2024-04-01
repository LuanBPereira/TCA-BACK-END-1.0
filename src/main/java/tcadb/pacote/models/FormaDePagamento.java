package tcadb.pacote.models;

public enum FormaDePagamento {
    DEBITO("Debito"),
    CREDITO("Credito"),
    PIX("Pix"),
    BOLETO("Boleto");

    private final String pagamento;

    FormaDePagamento(String pagamento) {
        this.pagamento = pagamento;
    }

}

