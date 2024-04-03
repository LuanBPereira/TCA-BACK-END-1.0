package tcadb.pacote;

import tcadb.pacote.dao.ProdutosDAO;
import tcadb.pacote.models.FormaDePagamento;
import tcadb.pacote.models.Produtos;
import tcadb.pacote.services.CarrinhoDeCompras;

import java.util.Locale;
import java.util.Scanner;

public class MainCliente {
    private static Scanner leitor = new Scanner(System.in).useDelimiter("\n");
    private static CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
    private static ProdutosDAO produtosDAO = new ProdutosDAO();

    public static void main(String[] args) {
        var opcao = exibirMenu();

        while (opcao != 4){
            switch (opcao) {
                case 1:
                    adicionarItemCarrinho();
                    break;
                case 2:
                    listarProdutosCarrinho();
                    break;
                case 3:
                    removerProdutoCarrinho();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            opcao = exibirMenu();
        }
        menuPagamento();
    }

    private static int exibirMenu(){
        System.out.println("""
               ===================================================
               ||            𝔹𝕖𝕞 𝕧𝕚𝕟𝕕𝕠 𝕒 𝕂𝕃 𝔻𝕠𝕔𝕖𝕤❕              ||
               ||             𝔼𝕤𝕔𝕠𝕝𝕙𝕒 𝕦𝕞𝕒 𝕠𝕡𝕔̧𝕒̃𝕠:                ||
               ||                                               ||
               || 1 - Adicionar produto ao carrinho de compras  ||
               || 2 - Listar produtos no carrinho               ||
               || 3 - Remover produto do carrinho               ||
               || 4 - Finalizar compra                          ||
               ||                                               ||
               ===================================================
                """);
        return leitor.nextInt();
    }

    private static void listarProdutosCarrinho(){
        carrinho.listarProdutosAdicionados();

    }

    private static void adicionarItemCarrinho() {
        String resposta = "";
        carrinho.listarProdutos();
        do {
            System.out.println("\nDigite o código do produto que deseja adicionar ao carrinho:");
            int codigoProduto = leitor.nextInt();
            leitor.nextLine();

            Produtos produto = produtosDAO.listarPorCodigo(codigoProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }
                System.out.println("Digite a quantidade de " + produto.getNome() + " que deseja:");
                int quantidade = leitor.nextInt();
                leitor.nextLine();

                carrinho.adicionarItem(produto, quantidade);
                System.out.println("Produto adicionado ao carrinho com sucesso!");

            System.out.println("\nDeseja adicionar outro produto? (S/N): ");
            resposta = leitor.nextLine();
        } while (resposta.equalsIgnoreCase("S"));
    }

    private static void removerProdutoCarrinho() {
        listarProdutosCarrinho();
        System.out.println("Digite o código do produto que deseja remover do carrinho:");
        int codigoProduto = leitor.nextInt();
        carrinho.removerItem(codigoProduto);
        System.out.println("Produto removido do carrinho com sucesso!");
    }

    private static void menuPagamento(){
        System.out.println("Produtos comprados: ");
        listarProdutosCarrinho();
        carrinho.calcularTotal();
        System.out.println("""
                \nQual a forma de pagamento você deseja?
                - Credito
                - Debito
                - Pix
                - Boleto""");
        // Limpa o buffer de entrada antes de ler a escolha de pagamento
        leitor.nextLine();
        String opcaoPagamento = leitor.nextLine().toUpperCase();

        switch (opcaoPagamento){
            case "CREDITO":
                FormaDePagamento.realizarPagamento(FormaDePagamento.CREDITO);
                break;
            case "DEBITO":
                FormaDePagamento.realizarPagamento(FormaDePagamento.DEBITO);
                break;
            case "PIX":
                FormaDePagamento.realizarPagamento(FormaDePagamento.PIX);
                break;
            case "BOLETO":
                FormaDePagamento.realizarPagamento(FormaDePagamento.BOLETO);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

}
