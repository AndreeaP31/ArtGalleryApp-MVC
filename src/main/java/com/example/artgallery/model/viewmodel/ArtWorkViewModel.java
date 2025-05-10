package com.example.artgallery.model.viewmodel;

import com.example.artgallery.model.ArtWork;
import com.example.artgallery.model.repository.ArtWorkRepository;
import com.example.artgallery.model.observer.Observable;

import java.util.ArrayList;
import java.util.List;

public class ArtWorkViewModel extends Observable {
    private final ArtWorkRepository repository;
    private List<ArtWork> artWorks;
    private ArtWork currentArtWork;

    public ArtWorkViewModel() {
        this.repository = new ArtWorkRepository();
        this.artWorks = new ArrayList<>(repository.getArtWorks());
        this.currentArtWork = new ArtWork();
    }

    public List<ArtWork> getArtWorks() {
        return artWorks;
    }

    public ArtWork getCurrentArtWork() {
        return currentArtWork;
    }

    public void setCurrentArtWork(ArtWork artWork) {
        this.currentArtWork = artWork;
        notifyObservers();
    }

    public void refresh() {
        repository.loadArtWorks();
        this.artWorks = repository.getArtWorks();
        notifyObservers();
    }

    public void addArtWork(ArtWork artWork) {
        if (repository.addArtWork(artWork)) {
            refresh();
        }
    }

    public void updateArtWork(int id, ArtWork artWork) {
        if (repository.updateArtWork(id, artWork)) {
            refresh();
        }
    }

    public void deleteArtWork(int id) {
        if (repository.deleteArtWork(id)) {
            refresh();
        }
    }

    public void searchByTitle(String title) {
        this.artWorks = repository.searchByTitle(title);
        notifyObservers();
    }

    public void filterByType(String type) {
        this.artWorks = repository.filterByType(type);
        notifyObservers();
    }

    public void filterByPriceRange(double min, double max) {
        this.artWorks = repository.filterByPriceRange(min, max);
        notifyObservers();
    }
}
