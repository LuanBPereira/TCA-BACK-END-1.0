package tcadb.pacote;

import tcadb.pacote.dao.ProdutosDAO;
import tcadb.pacote.models.Produtos;

import java.sql.SQLException;
import java.util.InputMismatchException;
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
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
                opcao = exibirMenu();
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        System.out.println("Encerrando programa...");
    }

    private static int exibirMenu() {
        int opcao;
        try {
            System.out.println("""
               ===================================================
               ||           𝔹𝕖𝕞 𝕧𝕚𝕟𝕕𝕠 𝕒 𝕂𝕃 𝔻𝕠𝕔𝕖𝕤❕               ||
               ||            𝔼𝕤𝕔𝕠𝕝𝕙𝕒 𝕦𝕞𝕒 𝕠𝕡𝕔̧𝕒̃𝕠:                 ||
               ||                                               ||
               || 1 - Listar produtos cadastrados               ||
               || 2 - Cadastrar novo produto                    ||
               || 3 - Remover um produto                        ||
               || 4 - Modificar dados de produto                ||
               || 5 - Finalizar programa                        ||
               ||                                               ||
               ===================================================
                """);
            opcao = leitor.nextInt();
            leitor.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Erro. Por favor, digite um número correspondente à opção desejada.\n");
            leitor.nextLine();
            opcao = exibirMenu();
        }
        return opcao;
    }

    private static void listarProdutosCadastrados() throws SQLException {
        ProdutosDAO produtosDAO = new ProdutosDAO();

        System.out.println("Produtos cadastrados: ");
        for(Produtos p : produtosDAO.listar()){
            System.out.println( "(CódigoP: " + p.getCodigoP() + ", " + p.getNome() + ","
                    + " R$" + p.getPreco() + ")" );
        }

        System.out.println("\nClique 'ENTER' para retornar ao menu");
        leitor.nextLine();
    }

    private static void cadastrarProdutos() throws SQLException {
        ProdutosDAO produtosDAO = new ProdutosDAO();
        Produtos produtos = new Produtos();
        Scanner leitura = new Scanner(System.in);

        System.out.println("Digite um produto para adicionar: ");
        produtos.setNome(leitura.nextLine());
        System.out.println("Digite um preço para o produto adicionado: ");
        produtos.setPreco(leitura.nextDouble());

        Produtos produtoCadastrado =  produtosDAO.cadastrar(produtos);
        System.out.println("Produto " + "[" + produtoCadastrado.getNome() +"]" + " cadastrado!");

        System.out.println("\nClique qualquer tecla para retornar ao menu");
        leitor.nextLine();
    }

    private static void removerProdutos() throws SQLException {
        ProdutosDAO produtosDAO = new ProdutosDAO();
        produtosDAO.listar().forEach(listar -> System.out.println("(CodigoP: " +listar.getCodigoP() + "," + listar.getNome() + "," + " R$" + listar.getPreco() + ")"));
        produtosDAO.remover();

        System.out.println("\nClique 'ENTER' para retornar ao menu");
        leitor.nextLine();
    }

    private static void modificarDadosProdutos(){
        ProdutosDAO produtosDAO = new ProdutosDAO();
        produtosDAO.listar().forEach(listar -> System.out.println("(CodigoP: " +listar.getCodigoP() + "," + listar.getNome() + "," + " R$" + listar.getPreco() + ")"));
        produtosDAO.modificar();

        System.out.println("\nClique 'ENTER' para retornar ao menu");
        leitor.nextLine();
    }

}