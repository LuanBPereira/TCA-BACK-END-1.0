package tcadb.pacote;


import tcadb.pacote.dao.ProdutosDAO;
import tcadb.pacote.produtos.Produtos;

import java.sql.SQLException;
import java.util.Scanner;


public class MainFuncionario {
    private static Scanner leitor = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {

        var opcao = exibirMenu();
        try {
            while (opcao != 4) {
                switch (opcao) {
                    case 1:
                        listarProdutosCadastrados();
                        break;
                    case 2:
                        cadastrarProdutos();
                        break;
                    case 3:
                        removerProdutos();
                        break;
                }
                opcao = exibirMenu();
            }
        } catch (SQLException e){
            e.printStackTrace();

        }
    }

    private static int exibirMenu() {
        System.out.println("""
                Lolja de Dolce - ESCOLHA UMA OPÇÃO:
                1 - Listar produtos cadastrados
                2 - Cadastrar novo produto
                3 - Remover um produto
                4 - Sair
                """);
        return leitor.nextInt();
    }

    public static void listarProdutosCadastrados() throws SQLException {
        ProdutosDAO produtosDAO = new ProdutosDAO();


        System.out.println("Produtos cadastrados: ");
        for(Produtos p : produtosDAO.listar()){
            System.out.println( "(" + p.getNome() + "," + " R$" + p.getPreco() + ")" );
        }

        //Conexao.getConexao().close();
        System.out.println("\nClique qualquer tecla para retornar ao menu");
        leitor.next();
    }

    public static void cadastrarProdutos() throws SQLException {
        ProdutosDAO produtosDAO = new ProdutosDAO();
        Produtos produtoCadastrado =  produtosDAO.cadastrar();
        System.out.println("Produto " + "[" +produtoCadastrado.getNome() +"]" + " cadastrado!");

        //Conexao.getConexao().close();
        System.out.println("\nClique qualquer tecla para retornar ao menu");
        leitor.next();
    }

    public static void removerProdutos() throws SQLException {
        ProdutosDAO produtosDAO = new ProdutosDAO();
        produtosDAO.listar().forEach(listar -> System.out.println("(" + listar.getNome() + "," + " R$" + listar.getPreco() + ")"));
        produtosDAO.remover();

        //Conexao.getConexao().close();
        System.out.println("\nClique qualquer tecla para retornar ao menu");
        leitor.next();
    }
}