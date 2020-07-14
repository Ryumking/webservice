package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;
    private final static String USER = "postgres";
    private final static String PASSWORD = "7753191v";
    private final static String URL = "jdbc:postgresql://localhost:5432/tasksDB";

    static {
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("characterEncoding", "utf8");
        config.addDataSourceProperty("serverTimezone", "Europe/Moscow");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setDriverClassName("org.postgresql.Driver");
        dataSource = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}