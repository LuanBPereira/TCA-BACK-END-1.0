package tcadb.pacote.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/tca_bd";
    private static final String USER = "root";
    private static final String PASSWORD = "666666"; // trocar a senha para "666666" quando for usar no curso

    private static Connection conn;

    private Conexao() {} // usado para não ser possível fazer uma instancia direta de Conexão.

    public static Connection getConexao() {
        try {
            conn = createDataSource().getConnection();
            return conn;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);

        return new HikariDataSource(config);
    }

}
