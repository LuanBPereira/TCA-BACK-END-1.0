package tcadb.pacote.services;

import tcadb.pacote.dao.ProdutosDAO;
import tcadb.pacote.models.Produtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarrinhoDeCompras {
    private List<ItemDeCompra> itens;

    public CarrinhoDeCompras() {
        this.itens = new ArrayList<>();
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

    public void removerItem(int codigoProduto) {
        // Encontra e remove o item do carrinho pelo código do produto
        itens.removeIf(item -> item.getProduto().getCodigoP().equals(codigoProduto));
    }

    public void listarProdutosNoCarrinho() {
        if (itens.isEmpty()) {
            System.out.println("O carrinho de compras está vazio.");
        } else {
            System.out.println("Produtos no Carrinho de Compras:");
            for (ItemDeCompra item : itens) {
                System.out.println("Produto: " + item.getProduto().getNome() +
                        ", Quantidade: " + item.getQuantidade() +
                        ", Preço Unitário: R$" + item.getProduto().getPreco() +
                        ", Subtotal: R$" + item.getSubtotal());
            }
        }
    }


    public List<ItemDeCompra> getItens() {
        return itens;
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemDeCompra item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }
}
