package tcadb.pacote.utils;

import tcadb.pacote.services.ItemDeCompra;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManipuladorArquivos {

    public void escreverNoArquivo(double valorTotalProdutos, double taxaDeEntrega, double valorTotalCompra, int opcaoPagamento, List<ItemDeCompra> itens) {
        DecimalFormat df = new DecimalFormat("0.00");
        String dataHoraFormatada = getDataHoraFormatada();
        String formaPagamento = getFormaPagamento(opcaoPagamento);

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("informacoes_compra.txt", true))) {
            escritor.write("\n----- Nova Compra -----\n");
            escritor.write("Data e horário da compra: " + dataHoraFormatada + "\n\n");
            escritor.write("Produtos comprados:\n");

            for (ItemDeCompra item : itens) {
                escritor.write("Codigo Produto: " + item.getProduto().getCodigoP() +
                        ", Produto: " + item.getProduto().getNome() +
                        ", Quantidade: " + item.getQuantidade() +
                        ", Preço Unitário: R$" + df.format(item.getProduto().getPreco()) +
                        ", Subtotal: R$" + df.format(item.getSubtotal()) + "\n");
            }

            escritor.write("\nValor total dos produtos: R$" + df.format(valorTotalProdutos) + "\n");
            escritor.write("\nTaxa de entrega: R$" + df.format(taxaDeEntrega) + "\n");
            escritor.write("Valor total da compra: R$" + df.format(valorTotalCompra) + "\n");
            escritor.write("Forma de pagamento: " + formaPagamento + "\n\n");
            System.out.println("Compra sucedida com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    private String getDataHoraFormatada() {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataHoraAtual.format(formatoDataHora);
    }

    private String getFormaPagamento(int opcaoPagamento){
        return switch (opcaoPagamento) {
            case 1 -> "Credito";
            case 2 -> "Debito";
            case 3 -> "PIX";
            case 4 -> "Boleto";
            default -> "Indefinida";
        };
    }

}
