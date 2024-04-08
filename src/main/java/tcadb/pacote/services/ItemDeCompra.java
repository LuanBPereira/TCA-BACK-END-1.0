package tcadb.pacote.services;

import tcadb.pacote.models.Produtos;

public class ItemDeCompra {

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
