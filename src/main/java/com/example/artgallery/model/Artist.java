package com.example.artgallery.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Artist {
    private IntegerProperty id;
    private StringProperty name;
    private ObjectProperty<LocalDate> birthDate;
    private StringProperty birthPlace;
    private StringProperty nationality;

    public Artist() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.birthDate = new SimpleObjectProperty<>();
        this.birthPlace = new SimpleStringProperty();
        this.nationality = new SimpleStringProperty();
    }

    public Artist(int id, String name, LocalDate birthDate, String birthPlace, String nationality) {
        this();
        this.id.set(id);
        this.name.set(name);
        this.birthDate.set(birthDate);
        this.birthPlace.set(birthPlace);
        this.nationality.set(nationality);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public ObjectProperty<LocalDate> birthDateProperty() { return birthDate; }
    public StringProperty birthPlaceProperty() { return birthPlace; }
    public StringProperty nationalityProperty() { return nationality; }
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public LocalDate getBirthDate() { return birthDate.get(); }
    public void setBirthDate(LocalDate birthDate) { this.birthDate.set(birthDate); }

    public String getBirthPlace() { return birthPlace.get(); }
    public void setBirthPlace(String birthPlace) { this.birthPlace.set(birthPlace); }

    public String getNationality() { return nationality.get(); }
    public void setNationality(String nationality) { this.nationality.set(nationality); }

    @Override
    public String toString() {
        return name.get() + " (" + nationality.get() + ")";
    }
}
