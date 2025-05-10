package com.example.artgallery;

import com.example.artgallery.controller.ArtistController;
import com.example.artgallery.controller.ArtWorkController;
import com.example.artgallery.view.ArtistView;
import com.example.artgallery.view.ArtWorkView;
import com.example.artgallery.model.viewmodel.ArtistViewModel;
import com.example.artgallery.model.viewmodel.ArtWorkViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        // Artist MVC
        ArtistViewModel artistVM = new ArtistViewModel();
        ArtistView artistView = new ArtistView(artistVM);
        new ArtistController(artistVM, artistView);

        // ArtWork MVC
        ArtWorkViewModel artworkVM = new ArtWorkViewModel();
        ArtWorkView artworkView = new ArtWorkView(artworkVM);
        new ArtWorkController(artworkVM, artworkView);

        // Tabs
        TabPane tabPane = new TabPane();
        Tab artistTab = new Tab("Artists", artistView);
        Tab artworkTab = new Tab("Artworks", artworkView);
        artistTab.setClosable(false);
        artworkTab.setClosable(false);
        tabPane.getTabs().addAll(artistTab, artworkTab);

        Scene scene = new Scene(tabPane, 700, 500);
        stage.setScene(scene);
        stage.setTitle("ArtGallery Manager");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
