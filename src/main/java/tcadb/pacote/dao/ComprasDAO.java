package tcadb.pacote.dao;

import tcadb.pacote.connection.Conexao;
import tcadb.pacote.services.ItemDeCompra;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ComprasDAO {
    // usei try-with-resources, pois ele fecha sozinho alguns métodos (observar linha 39 e 59).
    // sem a necessidade de usar o .close
    // achei mais interessante, pois é um jeito mais fácil e seguro de fechar alguns metodos
    // pois vai que eu esqueci de dar algum close, e colocando dentro do try(), ele se fecha sozinho.

    public void salvarCompra(double valorTotalProdutos, double taxaDeEntrega, double valorTotalCompra, int opcaoPagamento, List<ItemDeCompra> itens) {
        String dataHoraFormatada = getDataHoraFormatada();
        String formaPagamento = getFormaPagamento(opcaoPagamento);

        try (Connection conn = Conexao.getConexao()) {
            // Salvar dados da compra
            int compraId = salvarDadosCompra(conn, dataHoraFormatada, valorTotalProdutos, taxaDeEntrega, valorTotalCompra, formaPagamento);

            // Salvar os itens da compra
            if (compraId != -1) {
                salvarItensCompra(conn, compraId, itens);
                System.out.println("Compra salva no banco de dados.");
            } else {
                System.err.println("Falha ao salvar a compra.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar compra no banco de dados: " + e.getMessage());
        }
    }

    private int salvarDadosCompra(Connection conn, String dataHoraFormatada, double valorTotalProdutos, double taxaDeEntrega, double valorTotalCompra, String formaPagamento) throws SQLException {
        String sqlCompra = "INSERT INTO tb_compra (data_hora, valor_total_produtos, taxa_entrega, valor_total_compra, forma_pagamento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement psCompra = conn.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS)) {
            psCompra.setString(1, dataHoraFormatada);
            psCompra.setDouble(2, valorTotalProdutos);
            psCompra.setDouble(3, taxaDeEntrega);
            psCompra.setDouble(4, valorTotalCompra);
            psCompra.setString(5, formaPagamento);
            psCompra.executeUpdate();

            ResultSet generatedKeys = psCompra.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                System.err.println("Falha ao obter o ID da compra.");
                return -1;
            }
        }
    }

    private void salvarItensCompra(Connection conn, int compraId, List<ItemDeCompra> itens) throws SQLException {
        String sqlItens = "INSERT INTO tb_itens_compra (compra_id, codigo_produto, nome_produto, quantidade, preco_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psItens = conn.prepareStatement(sqlItens)) {
            for (ItemDeCompra item : itens) {
                psItens.setInt(1, compraId);
                psItens.setInt(2, item.getProduto().getCodigoP());
                psItens.setString(3, item.getProduto().getNome());
                psItens.setInt(4, item.getQuantidade());
                psItens.setDouble(5, item.getProduto().getPreco());
                psItens.setDouble(6, item.getSubtotal());
                psItens.executeUpdate();
            }
        }
    }

    private String getDataHoraFormatada() {
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
