package org.example;

import org.example.model.Band;
import org.example.model.Artist;

import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.sql.*;
import java.sql.ResultSet;
import java.util.LinkedList;

public class MaDbService {

    private Path tempDb;
    private Connection conn;
    //private final String bandFromNameQuery = "SELECT ID, FK_label_ID, name, countryOfOrigin, " +
    //        "countryOfOriginCode, location, formedIn, status FROM bands WHERE name = ?;";
    private final String bandFromNameQuery = "SELECT * FROM bands WHERE name = ?;";
    private final String artistsFromBandIdQuery = "SELECT * FROM artists a WHERE a.Id in (SELECT FK_artist_ID from bandMembers where FK_band_ID == ?);";

    public MaDbService(Connection conn) {
        this.conn = conn;
        System.out.println("Connection established");
    }

    public void close() {
        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Band> getBandByName(String name) {
        LinkedList<Band> res = new LinkedList<Band>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(bandFromNameQuery);
            pstmt.setString(1, name.replace(' ', '_')); //whitespace
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Band b = new Band(resultSet.getInt("ID"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getString("countryOfOrigin"),
                        resultSet.getString("countryOfOriginCode"),
                        resultSet.getString("status"),
                        resultSet.getString("formedIn"),
                        resultSet.getString("FK_label_ID"));
                res.add(b);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<Artist> getArtistsFromBandId(int bandId) {
        LinkedList<Artist> res = new LinkedList<Artist>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(artistsFromBandIdQuery);
            pstmt.setInt(1, bandId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Artist a = new Artist(resultSet.getInt("ID"),
                        resultSet.getString("name"));
                res.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    /*
    public void connect() {
        try (InputStream dbStream = App.class.getResourceAsStream("/" + this.dbName)) {
            tempDb = Files.createTempFile(this.dbName, ".sqlite");
            tempDb.toFile().deleteOnExit();
            if (dbStream == null) {
                throw new IOException("Unable to open database");
            }
            Files.copy(dbStream, tempDb, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        String connectionString = "jdbc:sqlite:" + tempDb.toAbsolutePath();
        try {
            conn = DriverManager.getConnection(connectionString);
            System.out.println("Connection established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
     */

}
