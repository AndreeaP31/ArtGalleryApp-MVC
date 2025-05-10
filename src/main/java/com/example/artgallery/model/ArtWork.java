package com.example.artgallery.model;

import javafx.beans.property.*;

public class ArtWork {
    private IntegerProperty id;
    private StringProperty title;
    private StringProperty type;
    private DoubleProperty price;
    private IntegerProperty artistId;

    public ArtWork() {
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.artistId = new SimpleIntegerProperty();
    }

    public ArtWork(int id, String title, String type, double price, int artistId) {
        this();
        this.id.set(id);
        this.title.set(title);
        this.type.set(type);
        this.price.set(price);
        this.artistId.set(artistId);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty titleProperty() { return title; }
    public StringProperty typeProperty() { return type; }
    public DoubleProperty priceProperty() { return price; }
    public IntegerProperty artistIdProperty() { return artistId; }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }

    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }

    public String getType() { return type.get(); }
    public void setType(String type) { this.type.set(type); }

    public double getPrice() { return price.get(); }
    public void setPrice(double price) { this.price.set(price); }

    public int getArtistId() { return artistId.get(); }
    public void setArtistId(int artistId) { this.artistId.set(artistId); }

    @Override
    public String toString() {
        return title.get() + " - " + type.get() + " ($" + price.get() + ")";
    }
}
