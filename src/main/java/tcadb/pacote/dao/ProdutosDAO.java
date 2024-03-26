package tcadb.pacote.dao;

import tcadb.pacote.conexao.Conexao;
import tcadb.pacote.produtos.Produtos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProdutosDAO {

    public Produtos cadastrar() {

        Produtos produtos = new Produtos();
        Scanner leitura = new Scanner(System.in);
        System.out.println("Digite um produto para adicionar: ");
        produtos.setNome(leitura.nextLine());
        System.out.println("Digite um preço para o produto adicionado: ");
        produtos.setPreco(leitura.nextDouble());


        String sql = "INSERT INTO tb_produtos (Nome, Preco)" +
                " VALUES (?, ?) ";

        Connection conn;
        PreparedStatement ps;

        try {
            conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            ps.setString(1, produtos.getNome());
            ps.setDouble(2, produtos.getPreco());


            ps.execute();
            ps.close();
            conn.close();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return produtos;
    }

    public List<Produtos> listar() {
        String sql = "select * from tb_produtos";

        List<Produtos> produtos = new ArrayList<>();

        Connection conn;
        PreparedStatement ps;
        ResultSet rs;

        try {
            conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(); // realização de consulta

            while (rs.next()) {
                Produtos produto = new Produtos();

                produto.setNome(rs.getString(1)); // recuperar nome SQL
                produto.setPreco(rs.getDouble(2)); // recuperar preco SQL

                produtos.add(produto);
            }


            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return produtos;
    }

    public void remover(){
        Scanner leitura = new Scanner(System.in);

        System.out.println("Qual produto gostaria de remover da tabela?");
        String escolhaRemocao = leitura.nextLine();

        String sql = "DELETE FROM tb_produtos WHERE Nome = ?";

        Connection conn;
        PreparedStatement ps;


        try{
            conn = Conexao.getConexao();
            ps = conn.prepareStatement(sql);

            ps.setString(1, escolhaRemocao);
            System.out.println("Produto " + escolhaRemocao + " removido com sucesso!");

            ps.execute();
            ps.close();
            conn.close();
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

