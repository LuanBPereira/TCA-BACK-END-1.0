package tcadb.pacote.services;

import tcadb.pacote.dao.ProdutosDAO;
import tcadb.pacote.models.Produtos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras implements ICarrinhoDeCompras {
    private DecimalFormat df = new DecimalFormat("0.00");
    private List<ItemDeCompra> itens;

    public CarrinhoDeCompras() {
        this.itens = new ArrayList<>();
    }

    public List<ItemDeCompra> getItens() {
        return itens;
    }

    public void adicionarItem(Produtos produto, int quantidade) {
        // Verifica se o produto já está no carrinho
        for (ItemDeCompra item : itens) {
            if (item.getProduto().getCodigoP().equals(produto.getCodigoP())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        // Se o produto não está no carrinho, adiciona um novo item
        itens.add(new ItemDeCompra(produto, quantidade));
    }

    public void removerItem(int codigoProduto, int quantidade) {
        for (ItemDeCompra item : itens) {
            if (item.getProduto().getCodigoP() == codigoProduto) {
                int quantidadeAtual = item.getQuantidade();
                if (quantidade > quantidadeAtual) {
                    System.out.println("Não é possível remover mais itens do que você tem.");
                    return;
                } else if (quantidadeAtual == quantidade) {
                    itens.remove(item);
                    System.out.println("Produto: " + item.getProduto().getNome() + " removido com sucesso!");
                } else {
                    item.setQuantidade(quantidadeAtual - quantidade);
                    System.out.println("Quantidade removida: " + quantidade + " do produto: " + item.getProduto().getNome());
                }
                return;
            }
        }
        // Se o item não for encontrado, exibe uma mensagem
        System.out.println("Produto não encontrado no carrinho.");
    }

    public void listarProdutosAdicionados() {
        // Verifica se há produtos no carrinho de compras
        if (itens.isEmpty()) {
            System.out.println("O carrinho de compras está vazio.");
        } else {
            System.out.println("Produtos no Carrinho de Compras:");
            for (ItemDeCompra item : itens) {
                System.out.println("Codigo Produto: " + item.getProduto().getCodigoP() +
                        ", Produto: " + item.getProduto().getNome() +
                        ", Quantidade: " + item.getQuantidade() +
                        ", Preço Unitário: R$" + df.format(item.getProduto().getPreco()) +
                        ", Subtotal: R$" + df.format(item.getSubtotal()));
            }
            System.out.println("\nValor total dos produtos: " + "R$" + df.format(calcularTotal()));
        }
    }

    public void listarProdutos() {
        ProdutosDAO produtosDAO = new ProdutosDAO();
        List<Produtos> produtos = produtosDAO.listar();
        for (Produtos produto : produtos) {
            System.out.println("Código: " + produto.getCodigoP() + " - " + produto.getNome() + " - R$" + df.format(produto.getPreco()));
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemDeCompra item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void limparCarrinho(){
        itens.clear();
    }

}
