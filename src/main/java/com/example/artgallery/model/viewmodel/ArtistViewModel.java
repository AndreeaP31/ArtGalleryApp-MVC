package com.example.artgallery.model.viewmodel;

import com.example.artgallery.model.Artist;
import com.example.artgallery.model.repository.ArtistRepository;
import com.example.artgallery.model.observer.Observable;

import java.util.ArrayList;
import java.util.List;

public class ArtistViewModel extends Observable {
    private final ArtistRepository repository;
    private List<Artist> artistList;
    private Artist currentArtist;

    public ArtistViewModel() {
        this.repository = new ArtistRepository();
        this.artistList = new ArrayList<>(repository.getArtistList());
        this.currentArtist = new Artist();
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public Artist getCurrentArtist() {
        return currentArtist;
    }

    public void setCurrentArtist(Artist artist) {
        this.currentArtist = artist;
        notifyObservers();
    }

    public void refresh() {
        repository.loadArtists();
        this.artistList = repository.getArtistList();
        notifyObservers();
    }

    public void addArtist(Artist artist) {
        if (repository.addArtist(artist)) {
            refresh();
        }
    }

    public void updateArtist(int id, Artist artist) {
        if (repository.updateArtist(id, artist)) {
            refresh();
        }
    }

    public void deleteArtist(int id) {
        if (repository.deleteArtist(id)) {
            refresh();
        }
    }

    public void searchByName(String name) {
        this.artistList = repository.searchByName(name);
        notifyObservers();
    }
}
