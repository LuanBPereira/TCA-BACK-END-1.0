package tcadb.pacote.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/tca_bd";
    private static final String USER = "root";
    private static final String PASSWORD = "55555"; // trocar a senha para "666666" quando for usar no curso

    private static Connection conn;

    private Conexao() {} // usado para não ser possível fazer uma instancia direta de Conexão.

    public static Connection getConexao() {
        try {
            if(conn == null) {
                //System.out.println("Conectando ao Banco de Dados...");
                // cria conexão com o BD
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            }
            //System.out.println("Conexão obtida com sucesso!\n");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
