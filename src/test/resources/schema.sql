CREATE TABLE IF NOT EXISTS bands (
    ID INTEGER PRIMARY KEY,
    FK_label_ID INTEGER DEFAULT NULL,
    name text NOT NULL,
    countryOfOrigin text,
    countryOfOriginCode text,
    location text,
    formedIn text,
    status text,
    FOREIGN KEY(FK_label_ID) REFERENCES labels(ID) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS artists (
    ID INTEGER PRIMARY KEY,
    name text NOT NULL
);

CREATE TABLE IF NOT EXISTS bandMembers (
    FK_artist_ID INTEGER NOT NULL,
    FK_band_ID INTEGER NOT NULL,
    status text,
    PRIMARY KEY(FK_artist_ID, FK_band_ID)
    FOREIGN KEY(FK_artist_ID) REFERENCES artists(ID) ON DELETE CASCADE,
    FOREIGN KEY(FK_band_ID) REFERENCES bands(ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS labels (
    ID INTEGER PRIMARY KEY,
    name text NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_bandMembers_band_id ON bandMembers(FK_band_ID);
CREATE INDEX IF NOT EXISTS idx_bandMembers_artist_id ON bandMembers(FK_artist_ID);
CREATE INDEX IF NOT EXISTS idx_bands_status ON bands(status);
CREATE INDEX IF NOT EXISTS idx_bands_country_code ON bands(countryOfOriginCode);
CREATE INDEX IF NOT EXISTS idx_bands_label ON bands(FK_label_ID)