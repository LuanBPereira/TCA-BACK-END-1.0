package tcadb.pacote.services;

import tcadb.pacote.models.Produtos;

public interface ICarrinhoDeCompras {
    void adicionarItem(Produtos produto, int quantidade);
    void removerItem(int codigoProduto, int quantidade);
    void listarProdutosAdicionados();
    void listarProdutos();
    double calcularTotal();
    void limparCarrinho();
}
