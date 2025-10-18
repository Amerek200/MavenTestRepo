package org.example.model;

import java.util.Objects;

public class Artist {
    public final int Id;
    public final String name;

    public Artist(int id, String name) {
        this.Id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist other = (Artist) o;
        return Objects.equals(this.Id, other.Id) && Objects.equals(this.name, other.name);
    }
}
