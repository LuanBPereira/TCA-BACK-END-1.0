package tcadb.pacote;


import tcadb.pacote.dao.ProdutosDAO;
import tcadb.pacote.produtos.Produtos;

import java.util.Scanner;


public class MainFuncionario {
    private static Scanner leitor = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {

        var opcao = exibirMenu();
        while (opcao != 3){
            switch (opcao){
                case 1:
                    listarProdutosCadastrados();
                    break;
                case 2:
                    cadastrarProdutos();
                    break;

            }
            opcao = exibirMenu();
        }


    }

    private static int exibirMenu() {
        System.out.println("""
                Lolja de Dolce - ESCOLHA UMA OPÇÃO:
                1 - Listar produtos cadastrados
                2 - Cadastrar novo produto
                3 - sair
                """);
        return leitor.nextInt();
    }

    public static void listarProdutosCadastrados(){
        ProdutosDAO produtosDAO = new ProdutosDAO();

        System.out.println("Produtos cadastrados: ");
        //produtosDAO.listar().forEach(listar -> System.out.println("(" + listar.getNome() + "," + " R$" + listar.getPreco() + ")"));
        for(Produtos p : produtosDAO.listar()){
            System.out.println( "(" + p.getNome() + "," + " R$" + p.getPreco() + ")" );
        }

        System.out.println("\nClique qualquer tecla para retornar ao menu");
        leitor.next();
    }

    public static void cadastrarProdutos(){
        ProdutosDAO produtosDAO = new ProdutosDAO();
        Produtos produtoCadastrado =  produtosDAO.cadastrar();
        System.out.println("Produto " + "[" +produtoCadastrado.getNome() +"]" + " cadastrado!");

        System.out.println("\nClique qualquer tecla para retornar ao menu");
        leitor.next();
    }
}