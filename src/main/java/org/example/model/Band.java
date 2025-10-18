package org.example.model;

import java.util.Objects;

public class Band {
    public final int Id;
    public final String name;
    public final String location;
    public final String countryOfOrigin;
    public final String countryOfOriginCode;
    public final String status;
    public final String formedIn;
    public final String labelId;

    public Band(int Id, String name, String location,
                String countryOfOrigin, String countryOfOriginCode,
                String status, String formedIn, String labelId) {
        this.Id = Id;
        this.name = name;
        this.location = location;
        this.countryOfOrigin = countryOfOrigin;
        this.countryOfOriginCode = countryOfOriginCode;
        this.status = status;
        this.formedIn = formedIn;
        this.labelId = labelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Band b = (Band) o;
        return Objects.equals(this.Id, b.Id) && Objects.equals(this.name, b.name) &&
                Objects.equals(this.location, b.location) && Objects.equals(this.countryOfOrigin, b.countryOfOrigin) &&
                Objects.equals(this.countryOfOriginCode, b.countryOfOriginCode) && Objects.equals(this.status, b.status) &&
                Objects.equals(this.formedIn, b.formedIn) && Objects.equals(this.labelId, b.labelId);
    }
}
