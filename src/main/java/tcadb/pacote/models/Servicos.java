package tcadb.pacote.models;

import tcadb.pacote.dao.ProdutosDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Servicos  {

    private Scanner leitura = new Scanner(System.in);
    ProdutosDAO produtosDAO = new ProdutosDAO();

    public static void main(String[] args) {
       Servicos servicos = new Servicos();
       servicos.comprar();
    }



    public void comprar() {

        produtosDAO.listar().forEach(listar -> System.out.println(listar.getNome())); // listando os produtos
        System.out.println("\nDigite o nome do produto para adicionar no seu carrinho de compras: ");
         String compras = leitura.nextLine();

        System.out.println("Digite a quantidade de produtos respectivamente: ");
         double compras2 = leitura.nextDouble();
        System.out.println(compras + " " + compras2);


    }

    public void precoTotal() {

    }

    public void escolherPagamento() {
        System.out.println("Qual a forma de pagamento voce deseja?");
        String pagamento = leitura.nextLine().toLowerCase();

        switch (pagamento) {
            case "debito":
                System.out.println(FormaDePagamento.DEBITO);
                break;
            case "credito":
                System.out.println(FormaDePagamento.CREDITO);
                break;
            case "pix":
                System.out.println(FormaDePagamento.PIX);
                break;
            case "boleto":
                System.out.println(FormaDePagamento.BOLETO);
                break;
            default:
                System.out.println("Forma de pagamento inválida.");
                break;
        }
    }

    public void carrinhoDeCompras(){

    }
}
