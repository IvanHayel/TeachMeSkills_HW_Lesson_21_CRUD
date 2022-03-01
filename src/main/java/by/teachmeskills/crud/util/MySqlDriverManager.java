package by.teachmeskills.crud.util;

import lombok.*;
import lombok.experimental.NonFinal;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Log
@Value
public class MySqlDriverManager implements AutoCloseable {
    private static final Path DATABASE_PROPERTY_PATH;

    static {
        try {
            URL databasePropertiesUrl = MySqlDriverManager.class.getClassLoader().getResource("database.properties");
            DATABASE_PROPERTY_PATH = Paths.get(databasePropertiesUrl.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw (IllegalStateException) new IllegalStateException().initCause(e);
        }
    }

    private static final Properties databaseProperties = new Properties();

    @EqualsAndHashCode.Exclude
    @NonFinal
    Connection connection;

    static {
        loadDatabaseProperties();
        registerDriver();
    }

    public MySqlDriverManager() {
        connection = setConnection();
    }

    @SneakyThrows(IOException.class)
    private static void loadDatabaseProperties() {
        @Cleanup InputStream propertiesStream = Files.newInputStream(DATABASE_PROPERTY_PATH);
        databaseProperties.load(propertiesStream);
    }

    @SneakyThrows(SQLException.class)
    private static void registerDriver() {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        log.info("JDBC MySQL Driver successfully registered.");
    }

    @SneakyThrows(SQLException.class)
    private Connection setConnection() {
        Properties databaseProperties = (Properties) MySqlDriverManager.databaseProperties.clone();
        String connectionUrl = (String) databaseProperties.remove("database.connection.URL");
        Connection connection = DriverManager.getConnection(connectionUrl, databaseProperties);
        log.info(String.format("Connected to %s", connection.getMetaData().getURL()));
        return connection;
    }

    @SneakyThrows(SQLException.class)
    public PreparedStatement prepareStatement(@NonNull String query) {
        if (connection == null) return null;
        if (connection.isClosed()) connection = setConnection();
        return connection.prepareStatement(query);
    }

    @Override
    @SneakyThrows(SQLException.class)
    public void close() {
        if (connection != null && !connection.isClosed()){
            String connectionUrl = connection.getMetaData().getURL();
            DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
            connection.close();
            log.info(String.format("Connection to %s successfully closed.", connectionUrl));
        }
    }
}