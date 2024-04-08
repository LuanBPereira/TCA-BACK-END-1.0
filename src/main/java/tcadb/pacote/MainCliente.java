package tcadb.pacote;

import tcadb.pacote.dao.ProdutosDAO;
import tcadb.pacote.models.FormaDePagamento;
import tcadb.pacote.models.Produtos;
import tcadb.pacote.services.CarrinhoDeCompras;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainCliente {
    private static Scanner leitor = new Scanner(System.in).useDelimiter("\n");
    private static CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
    private static ProdutosDAO produtosDAO = new ProdutosDAO();

    public static void main(String[] args) {
        var opcao = exibirMenu();
        // quero deixar uma dendo aqui: Confirmar se realmente é preciso limpar o carrinho
        // após a compra. Não sei se faz tanto sentido isso.
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
               || 5 - Sair do programa                          ||
               ||                                               ||
               ===================================================
                """);
        return leitor.nextInt();
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

            if(quantidade == 0){
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
        } while (resposta.equalsIgnoreCase("S"));
    }

    private static void listarProdutosCarrinho(){
        carrinho.listarProdutosAdicionados();

        System.out.println("\nClique 'ENTER' para retornar ao menu");
        leitor.next();
    }

    private static void removerProdutoCarrinho() {
        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio");
        } else {
            carrinho.listarProdutosAdicionados();
            System.out.println("\nDigite o código do produto que deseja remover do carrinho:");
            int codigoProduto = leitor.nextInt();
            System.out.println("Digite a quantidade que deseja remover:");
            int quantidade = leitor.nextInt();
            carrinho.removerItem(codigoProduto, quantidade);

            System.out.println("\nClique 'ENTER' para retornar ao menu");
            leitor.next();
        }
    }

    private static void menuPagamento() {
        DecimalFormat df = new DecimalFormat("0.00");
        final double TAXA_DE_ENTREGA = 7.00;
        final var VALOR_TOTAL_COMPRA = carrinho.calcularTotal() + TAXA_DE_ENTREGA;

        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio.");
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
                System.out.println("Por favor, insira um número válido para a opção de pagamento.\n");
                leitor.next(); // Limpa o buffer de entrada
                menuPagamento(); // Chama o método novamente para permitir que o usuário escolha novamente
            }
        }
        carrinho.limparCarrinho();
    }
}