package tcadb.pacote;

import java.util.Scanner;

public class MainCliente {
    private static Scanner leitor = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        var opcao = exibirMenu();

        while (opcao != 4){
            switch (opcao) {
                case 1:

            }
            opcao = exibirMenu();
        }
        System.out.println("Encerrando programa...");
    }

    private static int exibirMenu(){
        System.out.println("""
                ——————————————————————————————————————
                |        𝔹𝕖𝕞 𝕧𝕚𝕟𝕕𝕠 𝕒 𝕂𝕃 𝔻𝕠𝕔𝕖𝕤❕      |
                |         𝔼𝕤𝕔𝕠𝕝𝕙𝕒 𝕦𝕞𝕒 𝕠𝕡𝕔̧𝕒̃𝕠:         |
                | 1 - Listar produtos cadastrados    |
                | 2 - Cadastrar novo produto         |
                | 3 - Remover um produto             |
                | 4 - Modificar dados de produto     |
                | 5 - sair                           |
                ——————————————————————————————————————
                """);
        return leitor.nextInt();
    }
}
