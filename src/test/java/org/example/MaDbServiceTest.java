package org.example;

import org.example.model.Band;
import org.example.model.Artist;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.*;

import javax.swing.plaf.nimbus.State;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MaDbServiceTest {
    MaDbService service;
    Connection conn;

    @BeforeAll
    void setup() throws Exception {
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        try (InputStream in = getClass().getResourceAsStream("/schema.sql")) {
            if (in == null) throw new FileNotFoundException();
            String dbSchema = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(dbSchema);
            }
        }
        //setup of in memory database
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO artists VALUES (1, 'artist1');");
        stmt.execute("INSERT INTO artists VALUES (2, 'artist2');");
        stmt.execute("INSERT INTO artists VALUES (3, 'artist3');");
        stmt.execute("INSERT INTO artists VALUES (4, 'artist4');");
        stmt.execute("INSERT INTO labels VALUES (1, 'label1')");
        stmt.execute("INSERT INTO labels VALUES (2, 'label2')");
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO bands VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        List<Object[]> testBands = List.of(
            new Object[]{1, 1, "band1", "germany", "de", "hamburg", "2023", "active"},
            new Object[]{2, 2, "band1", "united states", "us", "georgia", "2020", "active"},
            new Object[]{3, 1, "band2", "germany", "de", "kiel", "2022", "active"},
            new Object[]{4, 2, "band3", "germany", "de", "kiel", "1999", "disbanded"}
        );
        for (Object[] band : testBands) {
            pstmt.setInt(1, (int)band[0]);
            pstmt.setInt(2, (int)band[1]);
            pstmt.setString(3, (String) band[2]);
            pstmt.setString(4, (String) band[3]);
            pstmt.setString(5, (String) band[4]);
            pstmt.setString(6, (String) band[5]);
            pstmt.setString(7, (String) band[6]);
            pstmt.setString(8, (String) band[7]);
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        //artistID, bandId, status
        stmt.execute("INSERT INTO bandMembers VALUES (1, 1, 'current')");
        stmt.execute("INSERT INTO bandMembers VALUES (2, 1, 'current')");
        stmt.execute("INSERT INTO bandMembers VALUES (3, 2, 'current')");
        stmt.execute("INSERT INTO bandMembers VALUES (1, 3, 'current')");
        stmt.execute("INSERT INTO bandMembers VALUES (1, 4, 'current')");
        stmt.execute("INSERT INTO bandMembers VALUES (4, 4, 'current')");
        service = new MaDbService(conn);
    }

    @AfterAll
    void tearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) { conn.close(); }
    }

    @Test
    void getBandsByNameTest() {
        List<Band> result = service.getBandByName("band1");
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getSingleBandByNameTest() {
        List<Band> result = service.getBandByName("band2");
        Assertions.assertEquals(1, result.size());
        Band fetchedBand = result.getFirst();
        Band expectedBand = new Band(3, "band2", "kiel", "germany",
                "de",  "active", "2022", "1");
        Assertions.assertEquals(expectedBand, fetchedBand);
    }

    @Test
    void getArtistsByBandIdTest() {
        List<Artist> result = service.getArtistsFromBandId(1);
        Artist[] expectedArtists = new Artist[] { new Artist(1, "artist1"), new  Artist(2, "artist2")};
        Assertions.assertTrue(result.containsAll(List.of(expectedArtists)));
    }

    @Disabled //used for testing with prod db copy
    @Test
    void getBandByNameTestOld() {
        List<Band> result = service.getBandByName("Nemesis");
        Assertions.assertEquals(49, result.size());
    }

    @Disabled //used for testing with prod db copy
    @Test
    void getArtistsByBandIdTestOld() {
        List<Artist> result = service.getArtistsFromBandId(29);
        Artist artist1 = new Artist(7678, "Silenius");
        Artist artist2 = new Artist(7679, "Protector");
        List<Artist> expected = Arrays.asList(artist1, artist2);
        Assertions.assertTrue(result.containsAll(expected));
    }



}




