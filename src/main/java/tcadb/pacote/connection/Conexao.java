package tcadb.pacote.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/tca_bd";
    private static final String USER = "root";
    private static final String PASSWORD = "55555"; // trocar a senha para "666666" quando for usar no curso
    private static final int MAX_POOL_SIZE = 10; //  número máximo de conexões como uma constante para fácil configuração
    private static final HikariDataSource dataSource = createDataSource(); // pool de conexões Hikari para gerenciar as conexões melhor
                                                                            // melhorando o desempenho principalmente

    private Conexao() {} // usado para não ser possível fazer uma instancia direta de Conexão.

    public static Connection getConexao() {
        try {
            return dataSource.getConnection(); // Retorna uma conexão do pool
        } catch (SQLException e) {
            // Melhorar o tratamento de exceções para fornecer informações úteis para depuração
            throw new RuntimeException("Erro ao obter conexão com o banco de dados", e);
        }
    }

    private static HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(MAX_POOL_SIZE);

        return new HikariDataSource(config);
    }

}
