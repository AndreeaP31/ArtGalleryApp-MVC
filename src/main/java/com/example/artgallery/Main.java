package com.example.artgallery;

import com.example.artgallery.controller.ArtistController;
import com.example.artgallery.controller.ArtWorkController;
import com.example.artgallery.view.ArtistView;
import com.example.artgallery.view.ArtWorkView;
import com.example.artgallery.model.viewmodel.ArtistViewModel;
import com.example.artgallery.model.viewmodel.ArtWorkViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private ComboBox<String> languageSelector;

    @Override
    public void start(Stage stage) {
        // initializează selectorul de limbă
        languageSelector = new ComboBox<>();
        languageSelector.getItems().addAll("en", "fr", "de", "ro");
        languageSelector.setValue("en"); // default EN

        languageSelector.setOnAction(e -> {
            String lang = languageSelector.getValue();
            loadUI(lang, stage);
        });

        loadUI("en", stage); // inițializare default
    }

    private void loadUI(String langCode, Stage stage) {
        Locale locale = new Locale(langCode);
        ResourceBundle bundle = ResourceBundle.getBundle("labels", locale);

        // Artist MVC
        ArtistViewModel artistVM = new ArtistViewModel();
        ArtistView artistView = new ArtistView(artistVM, bundle);
        new ArtistController(artistVM, artistView);

        // ArtWork MVC
        ArtWorkViewModel artworkVM = new ArtWorkViewModel();
        ArtWorkView artworkView = new ArtWorkView(artworkVM, bundle);
        new ArtWorkController(artworkVM, artworkView);

        // Tabs
        TabPane tabPane = new TabPane();
        Tab artistTab = new Tab(bundle.getString("label.name"), artistView);
        Tab artworkTab = new Tab(bundle.getString("label.title"), artworkView);
        artistTab.setClosable(false);
        artworkTab.setClosable(false);
        tabPane.getTabs().addAll(artistTab, artworkTab);

        VBox root = new VBox(languageSelector, tabPane);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle(bundle.getString("app.title"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
