package org.example;

import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try (Connection conn = createConnection("MA.sqlite3")) {
            MaDbService dbService = new MaDbService(conn);
            System.out.println(dbService.getArtistsFromBandId(29));
            dbService.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to create Db connection: " + e.getMessage());
        }

        Calculator calculator = new Calculator();
        System.out.println(calculator.divide(15.0, 0));
    }

    protected static Connection createConnection(String dbName) throws IOException, SQLException {
        InputStream dbStream = App.class.getResourceAsStream("/" + dbName);
        Path tempDbPath = Files.createTempFile(dbName, ".sqlite3");
        tempDbPath.toFile().deleteOnExit();
        if (dbStream == null) {
            throw new IOException("Unable to open database.");
        }
        Files.copy(dbStream, tempDbPath, StandardCopyOption.REPLACE_EXISTING);
        String connectionString = "jdbc:sqlite:" + tempDbPath.toAbsolutePath();
        return DriverManager.getConnection(connectionString);
    }
}
