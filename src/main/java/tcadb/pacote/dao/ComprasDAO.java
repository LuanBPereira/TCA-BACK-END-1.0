package tcadb.pacote.dao;

import tcadb.pacote.connection.Conexao;
import tcadb.pacote.services.ItemDeCompra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ComprasDAO {

    public void salvarCompra(double valorTotalProdutos, double taxaDeEntrega, double valorTotalCompra, int opcaoPagamento, List<ItemDeCompra> itens) {
        String dataHoraFormatada = getDataHoraFormatada();
        String formaPagamento = getFormaPagamento(opcaoPagamento);
        // usei try-with-resources, pois ele fecha sozinho alguns métodos (observar linha 25 e 36).
        // sem a necessidade de usar o .close
        // achei mais interessante, pois é um jeito mais fácil e seguro de fechar alguns metodos
        // pois vai que eu esqueci de dar algum close, e colocando dentro do try(), ele se fecha sozinho.
        try (Connection conn = Conexao.getConexao()) {
            // salva dados da compra
            String sqlCompra = "INSERT INTO tb_compra (data_hora, valor_total_produtos, taxa_entrega, valor_total_compra, forma_pagamento) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psCompra = conn.prepareStatement(sqlCompra)) {
                psCompra.setString(1, dataHoraFormatada);
                psCompra.setDouble(2, valorTotalProdutos);
                psCompra.setDouble(3, taxaDeEntrega);
                psCompra.setDouble(4, valorTotalCompra);
                psCompra.setString(5, formaPagamento);
                psCompra.executeUpdate();
            }

            // salva os dados dos itens que foram comprados
            String sqlItens = "INSERT INTO tb_itens_compra (codigo_produto, nome_produto, quantidade, preco_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psItens = conn.prepareStatement(sqlItens)) {
                for (ItemDeCompra item : itens) {
                    psItens.setInt(1, item.getProduto().getCodigoP());
                    psItens.setString(2, item.getProduto().getNome());
                    psItens.setInt(3, item.getQuantidade());
                    psItens.setDouble(4, item.getProduto().getPreco());
                    psItens.setDouble(5, item.getSubtotal());
                    psItens.executeUpdate();
                }
            }

            System.out.println("Compra salva no banco de dados.");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar compra no banco de dados: " + e.getMessage());
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
