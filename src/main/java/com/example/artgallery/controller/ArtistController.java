package com.example.artgallery.controller;

import com.example.artgallery.model.Artist;
import com.example.artgallery.view.ArtistView;
import com.example.artgallery.model.viewmodel.ArtistViewModel;
import javafx.scene.input.KeyCode;

public class ArtistController {
    private final ArtistViewModel viewModel;
    private final ArtistView view;

    public ArtistController(ArtistViewModel viewModel, ArtistView view) {
        this.viewModel = viewModel;
        this.view = view;
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getBtnAdd().setOnAction(e -> {
            Artist artist = view.getArtistFromForm();
            viewModel.addArtist(artist);
        });

        view.getBtnUpdate().setOnAction(e -> {
            Artist selected = view.getSelectedArtist();
            if (selected != null) {
                Artist updated = view.getArtistFromForm();
                viewModel.updateArtist(selected.getId(), updated);
            }
        });

        view.getBtnDelete().setOnAction(e -> {
            Artist selected = view.getSelectedArtist();
            if (selected != null) {
                viewModel.deleteArtist(selected.getId());
            }
        });

        view.getTxtSearch().setOnKeyReleased(e -> {
            String name = view.getTxtSearch().getText();
            if (name.isBlank()) {
                viewModel.refresh();
            } else {
                viewModel.searchByName(name);
            }
        });
    }
}
