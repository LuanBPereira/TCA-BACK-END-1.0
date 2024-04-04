package tcadb.pacote.services;

import tcadb.pacote.models.Produtos;

public class ItemDeCompra extends Produtos {

    private Produtos produto;
    private int quantidade;

    public ItemDeCompra(Produtos produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produtos getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }

}












//    public void comprar() {
//
//        produtosDAO.listar().forEach(listar -> System.out.println(listar.getNome())); // listando os produtos
//        System.out.println("\nDigite o nome do produto para adicionar no seu carrinho de compras: ");
//        String compras = leitura.nextLine();
//
//        System.out.println("Digite a quantidade de produtos respectivamente: ");
//        double compras2 = leitura.nextDouble();
//        System.out.println(compras + " " + compras2);
//
//
//    }




//    public void escolherPagamento() {
//        System.out.println("Qual a forma de pagamento voce deseja?");
//        String pagamento = leitura.nextLine().toLowerCase();
//
//        switch (pagamento) {
//            case "debito":
//                System.out.println(FormaDePagamento.DEBITO);
//                break;
//            case "credito":
//                System.out.println(FormaDePagamento.CREDITO);
//                break;
//            case "pix":
//                System.out.println(FormaDePagamento.PIX);
//                break;
//            case "boleto":
//                System.out.println(FormaDePagamento.BOLETO);
//                break;
//            default:
//                System.out.println("Forma de pagamento inválida.");
//                break;
//        }
//    }
//}
