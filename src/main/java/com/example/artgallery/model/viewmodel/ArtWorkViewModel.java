package com.example.artgallery.model.viewmodel;

import com.example.artgallery.model.ArtWork;
import com.example.artgallery.model.repository.ArtWorkRepository;
import com.example.artgallery.model.observer.Observable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    // CSV
    public void exportToCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            writer.println("Title,Type,Price,ArtistID");
            for (ArtWork art : artWorks) {
                writer.printf("%s,%s,%.2f,%d%n",
                        art.getTitle(),
                        art.getType(),
                        art.getPrice(),
                        art.getArtistId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DOC
    public void exportToDoc(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Art Gallery Report\n\n");
            for (ArtWork art : artWorks) {
                writer.write(String.format(
                        "Title: %s\nType: %s\nPrice: %.2f\nArtist ID: %d\n\n",
                        art.getTitle(),
                        art.getType(),
                        art.getPrice(),
                        art.getArtistId()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Map<String, Long> getArtTypeStats() {
        return artWorks.stream()
                .collect(Collectors.groupingBy(ArtWork::getType, Collectors.counting()));
    }
    public void filterByArtist(int artistId) {
        this.artWorks = repository.getArtWorks().stream()
                .filter(a -> a.getArtistId() == artistId)
                .toList();
        notifyObservers();
    }
    public void filterByArtistName(String name) {
        this.artWorks = repository.searchByArtistName(name);
        notifyObservers();
    }



}
