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

    public static void realizarPagamento(FormaDePagamento formaDePagamento) {
        switch (formaDePagamento) {
            case CREDITO:
                System.out.println("Pagamento realizado com cartão de crédito.");
                break;
            case DEBITO:
                System.out.println("Pagamento realizado com cartão de débito.");
                break;
            case PIX:
                System.out.println("Pagamento realizado com PIX.");
                break;
            case BOLETO:
                System.out.println("Pagamento realizado com boleto bancário.");
                break;
        }
    }
}

