package tcadb.pacote;

import tcadb.pacote.dao.ProdutosDAO;
import tcadb.pacote.models.Produtos;
import tcadb.pacote.services.CarrinhoDeCompras;
import tcadb.pacote.services.ItemDeCompra;

import java.util.List;
import java.util.Scanner;

public class MainCliente {
    private static Scanner leitor = new Scanner(System.in).useDelimiter("\n");
    private static CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
    private static ProdutosDAO produtosDAO = new ProdutosDAO();


    public static void main(String[] args) {
        var opcao = exibirMenu();

        while (opcao != 5){
            switch (opcao) {
                case 1:
                    listarProdutosDisponiveis();
                    break;
                case 2:
                    adicionarItemCarrinhoDeCompras();
                    break;
                case 3:
                    listarProdutosCarrinho();
                    break;
                case 4:
                    removerProdutoCarrinho();
                default:
                    System.out.println("Opção inválida. Tente novamente.");

            }
            opcao = exibirMenu();
        }
        System.out.println("Encerrando programa...");
    }

    private static int exibirMenu(){
        System.out.println("""
                ——————————————————————————————————————
                |        𝔹𝕖𝕞 𝕧𝕚𝕟𝕕𝕠 𝕒 𝕂𝕃 𝔻𝕠𝕔𝕖𝕤❕      |
                |         𝔼𝕤𝕔𝕠𝕝𝕙𝕒 𝕦𝕞𝕒 𝕠𝕡𝕔̧𝕒̃𝕠:         |
                | 1 - Listar produtos disponiveis   |
                | 2 - Adicionar produto ao carrinho de compras  |
                | 3 - Listar produtos no carrinho           |
                | 4 - Remover produto do carrinho
                  5 - Finalizar compra
                |                    |
                ——————————————————————————————————————
                """);
        return leitor.nextInt();
    }
    private static void listarProdutosDisponiveis() {
        List<Produtos> produtos = produtosDAO.listar();
        for (Produtos produto : produtos) {
            System.out.println(produto.getCodigoP() + " - " + produto.getNome() + " - R$" + produto.getPreco());
        }
    }

    public static void listarProdutosCarrinho(){
            carrinho.listarProdutosAdicionados();

    }

    private static void adicionarItemCarrinhoDeCompras() {
        System.out.println("Digite o código do produto que deseja adicionar ao carrinho:");
        int codigoProduto = leitor.nextInt();
        Produtos produto = produtosDAO.listarPorId(codigoProduto);
        if (produto != null) {
            System.out.println("Digite a quantidade desejada:");
            int quantidade = leitor.nextInt();
            carrinho.adicionarItem(produto, quantidade);
            System.out.println("Produto adicionado ao carrinho com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void removerProdutoCarrinho() {
        System.out.println("Digite o código do produto que deseja remover do carrinho:");
        int codigoProduto = leitor.nextInt();
        carrinho.removerItem(codigoProduto);
        System.out.println("Produto removido do carrinho com sucesso!");
    }
}
