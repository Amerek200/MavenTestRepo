package org.example;

import org.example.model.Band;
import org.junit.jupiter.api.*;

public class BandTest {

    Band band1;
    Band band2;

    @BeforeEach
    void setup() {
        band1 = new Band(1, "Band1", "loc1",
                "coo", "cooCode", "active",
                "1993", "lID");
        band2 = new Band(1, "Band1", "loc1",
                "coo", "cooCode", "active",
                "1993", "lID");
    }

    @Test
    @DisplayName("Testing reference sameness of Bands")
    void sameTest() {
        Assertions.assertNotSame(band1, band2);
        band2 = band1;
        Assertions.assertSame(band1, band2);
    }

    @Test
    @DisplayName("Testing equals implementation of Bands")
    void equalsTest() {
        Assertions.assertEquals(band1, band2);
        Band band3 = new Band(3, "Band1", "loc1",
                "coo", "cooCode", "active",
                "1993", "lID");
        Assertions.assertNotEquals(band1, band3);
    }
}