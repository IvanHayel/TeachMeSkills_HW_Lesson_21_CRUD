package by.teachmeskills.crud.util;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Value
public class MySqlScriptsManager {
    private static final Path MYSQL_SCRIPTS_PATH;

    static {
        URL sqlUrl = MySqlScriptsManager.class.getClassLoader().getResource("sql");
        try {
            MYSQL_SCRIPTS_PATH = Paths.get(sqlUrl.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw (IllegalStateException) new IllegalStateException().initCause(e);
        }
    }

    Map<String, String> queries = new HashMap<>();
    MySqlScriptsVisitor visitor = new MySqlScriptsVisitor(queries);

    @SneakyThrows(IOException.class)
    public MySqlScriptsManager() {
        Files.walkFileTree(MYSQL_SCRIPTS_PATH, visitor);
    }

    public String getQuery(@NonNull String queryName) {
        return queries.getOrDefault(queryName, null);
    }
}