package org.testando;


import org.testando.dao.ProdutosDAO;


public class Main {
    public static void main(String[] args) {

        new ProdutosDAO().cadastrarProduto();
    }
}