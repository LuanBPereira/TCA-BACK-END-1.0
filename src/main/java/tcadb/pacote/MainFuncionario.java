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
            while (opcao != 5) {
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
                    case 4:
                        modificarDadosProdutos();
                }
                opcao = exibirMenu();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Encerrando programa...");
    }

    private static int exibirMenu() {
        System.out.println("""
                ——————————————————————————————————————
                |        𝔹𝕖𝕞 𝕧𝕚𝕟𝕕𝕠 𝕒 𝕂𝕃 𝔻𝕠𝕔𝕖𝕤❕       |
                |         𝔼𝕤𝕔𝕠𝕝𝕙𝕒 𝕦𝕞𝕒 𝕠𝕡𝕔̧𝕒̃𝕠:         |
                | 1 - Listar produtos cadastrados    |
                | 2 - Cadastrar novo produto         |
                | 3 - Remover um produto             |
                | 4 - Modificar dados de produto     |
                | 5 - sair                           |
                ——————————————————————————————————————
                """);
        return leitor.nextInt();
    }

    public static void listarProdutosCadastrados() throws SQLException {
        ProdutosDAO produtosDAO = new ProdutosDAO();

        System.out.println("Produtos cadastrados: ");
        for(Produtos p : produtosDAO.listar()){
            System.out.println( "(" + p.getCodigoP() + ", " + p.getNome() + ","
                    + " R$" + p.getPreco() + ")" );
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

    public static void modificarDadosProdutos(){
        ProdutosDAO produtosDAO = new ProdutosDAO();
        produtosDAO.modificar();

        System.out.println("\nClique qualquer tecla para retornar ao menu");
        leitor.next();
    }
}