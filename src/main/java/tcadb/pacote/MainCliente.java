package tcadb.pacote;

import tcadb.pacote.dao.ComprasDAO;
import tcadb.pacote.dao.ProdutosDAO;
import tcadb.pacote.models.FormaDePagamento;
import tcadb.pacote.models.Produtos;
import tcadb.pacote.services.CarrinhoDeCompras;
import tcadb.pacote.utils.ManipuladorArquivos;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainCliente {
    private static Scanner leitor = new Scanner(System.in).useDelimiter("\n");
    private static CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
    private static ProdutosDAO produtosDAO = new ProdutosDAO();
    private static ManipuladorArquivos manipuladorArquivos = new ManipuladorArquivos();
    private static ComprasDAO dadosDeCompraDAO = new ComprasDAO();

    public static void main(String[] args) {
        var opcao = exibirMenu();
        while (opcao != 5){
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
                    case 4:
                        menuPagamento();
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            opcao = exibirMenu();
        }
        System.out.println("Encerrando programa...");
    }

    private static int exibirMenu(){
        int opcao;
        try {
            System.out.println("""
               ===================================================
               ||            𝔹𝕖𝕞 𝕧𝕚𝕟𝕕𝕠 𝕒 𝕂𝕃 𝔻𝕠𝕔𝕖𝕤❕             ||
               ||             𝔼𝕤𝕔𝕠𝕝𝕙𝕒 𝕦𝕞𝕒 𝕠𝕡𝕔̧𝕒̃𝕠:                ||
               ||                                               ||
               || 1 - Adicionar produto ao carrinho de compras  ||
               || 2 - Listar produtos no carrinho               ||
               || 3 - Remover produto do carrinho               ||
               || 4 - Finalizar compra                          ||
               || 5 - Sair do programa                          ||
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

    private static void adicionarItemCarrinho() {
        String resposta = "";
        carrinho.listarProdutos();
        do {
            try {

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

                if (quantidade == 0) {
                    System.out.println("Erro. Quantidade de produto precisa ser maior que 0.\n");
                    adicionarItemCarrinho();
                    return;
                }
                leitor.nextLine();
                carrinho.adicionarItem(produto, quantidade);
                System.out.println("Produto adicionado ao carrinho com sucesso!");

                System.out.println("\nDeseja adicionar outro produto? CLique 'S' para continuar a comprar" +
                        " ou clique 'ENTER' para retornar ao menu.");
                resposta = leitor.nextLine();

            } catch (InputMismatchException e) {
                System.out.println("Erro. Por favor, insira um número válido para quantidade ou código do produto.");
                leitor.next();
            }
        } while (resposta.equalsIgnoreCase("S"));
    }

    private static void listarProdutosCarrinho(){
        carrinho.listarProdutosAdicionados();

        System.out.println("\nClique 'ENTER' para retornar ao menu");
        leitor.nextLine();
    }

    private static void removerProdutoCarrinho() {
        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho de compras vazio.");
            System.out.println("\nClique 'ENTER' para retornar ao menu");
            leitor.nextLine();
        } else {
            carrinho.listarProdutosAdicionados();
            try {
                System.out.println("\nDigite o código do produto que deseja remover do carrinho:");
                int codigoProduto = leitor.nextInt();
                System.out.println("Digite a quantidade que deseja remover:");
                int quantidade = leitor.nextInt();
                carrinho.removerItem(codigoProduto, quantidade);

                System.out.println("\nClique 'ENTER' para retornar ao menu");
                leitor.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Erro. Por favor, insira um número válido para quantidade ou código do produto.");
            }
        }
    }

    private static void menuPagamento() {
        DecimalFormat df = new DecimalFormat("0.00");
        final double TAXA_DE_ENTREGA = 7.00;
        final double VALOR_TOTAL_COMPRA = carrinho.calcularTotal() + TAXA_DE_ENTREGA;

        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho de compras vazio.");
            System.out.println("\nClique 'ENTER' para retornar ao menu");
            leitor.nextLine();
        } else {
            carrinho.listarProdutosAdicionados();
            System.out.println("\nTaxa de entrega: R$" + df.format(TAXA_DE_ENTREGA) +
                    "\nValor total da compra: R$" + df.format(VALOR_TOTAL_COMPRA));

            System.out.println("""
                    \nQual a forma de pagamento você deseja? Escolha de 1 a 4.
                    1 - Credito
                    2 - Debito
                    3 - Pix
                    4 - Boleto""");

            int opcaoPagamento = leitor.nextInt();
            // tenho que ver esse try-catch, pois não tá tendo nenhum efeito.
            try {
                switch (opcaoPagamento) {
                    case 1:
                        FormaDePagamento.realizarPagamento(FormaDePagamento.CREDITO);
                        break;
                    case 2:
                        FormaDePagamento.realizarPagamento(FormaDePagamento.DEBITO);
                        break;
                    case 3:
                        FormaDePagamento.realizarPagamento(FormaDePagamento.PIX);
                        break;
                    case 4:
                        FormaDePagamento.realizarPagamento(FormaDePagamento.BOLETO);
                        break;
                    default:
                        System.out.println("Opção inválida.\n");
                       menuPagamento();
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro. Por favor, digite uma das opções de pagamento.\n");
                leitor.nextLine();
                menuPagamento(); // Chama o método novamente para permitir que o usuário escolha novamente
            }

          manipuladorArquivos.escreverNoArquivo(carrinho.calcularTotal(), TAXA_DE_ENTREGA, VALOR_TOTAL_COMPRA, opcaoPagamento, carrinho.getItens());
          dadosDeCompraDAO.salvarCompra(carrinho.calcularTotal(), TAXA_DE_ENTREGA, VALOR_TOTAL_COMPRA, opcaoPagamento, carrinho.getItens());
        }
        carrinho.limparCarrinho();
    }
}