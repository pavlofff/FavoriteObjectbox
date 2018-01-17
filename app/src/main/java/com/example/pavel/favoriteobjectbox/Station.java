package com.example.pavel.favoriteobjectbox;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Station {
    @Id long id;
    String Name;
    String url;
    boolean favorite;

    public Station() {
    }

    public Station(String name) {
        Name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
