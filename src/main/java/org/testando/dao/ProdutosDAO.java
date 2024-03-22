package org.testando.dao;

import org.testando.conexao.Conexao;
import org.testando.produtos.Produtos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ProdutosDAO {

    public void cadastrarProduto() {

        Produtos produtos = new Produtos();
        Scanner leitura = new Scanner(System.in);
        System.out.println("Digite um produto para adicionar: ");
        produtos.setNome(leitura.nextLine());
        System.out.println("digite um preco");
        produtos.setPreco(leitura.nextDouble());


        String sql = "INSERT INTO tb_produtos (Nome, Preco)" +
                " VALUES (?, ?) ";

        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, produtos.getNome());
            ps.setDouble(2, produtos.getPreco());


            ps.execute();
            ps.close();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}
