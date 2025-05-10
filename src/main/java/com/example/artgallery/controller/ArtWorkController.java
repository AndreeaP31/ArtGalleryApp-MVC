package com.example.artgallery.controller;

import com.example.artgallery.model.ArtWork;
import com.example.artgallery.view.ArtWorkView;
import com.example.artgallery.model.viewmodel.ArtWorkViewModel;

public class ArtWorkController {
    private final ArtWorkViewModel viewModel;
    private final ArtWorkView view;

    public ArtWorkController(ArtWorkViewModel viewModel, ArtWorkView view) {
        this.viewModel = viewModel;
        this.view = view;
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getBtnAdd().setOnAction(e -> {
            ArtWork art = view.getArtWorkFromForm();
            viewModel.addArtWork(art);
        });

        view.getBtnUpdate().setOnAction(e -> {
            ArtWork selected = view.getSelectedArtWork();
            if (selected != null) {
                ArtWork updated = view.getArtWorkFromForm();
                viewModel.updateArtWork(selected.getId(), updated);
            }
        });

        view.getBtnDelete().setOnAction(e -> {
            ArtWork selected = view.getSelectedArtWork();
            if (selected != null) {
                viewModel.deleteArtWork(selected.getId());
            }
        });

        view.getTxtSearch().setOnKeyReleased(e -> {
            String title = view.getTxtSearch().getText();
            if (title.isBlank()) {
                viewModel.refresh();
            } else {
                viewModel.searchByTitle(title);
            }
        });
    }
}
