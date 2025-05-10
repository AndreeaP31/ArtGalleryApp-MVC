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
        view.getBtnExportCSV().setOnAction(e -> {
            viewModel.exportToCSV("artworks.csv");
        });

        view.getBtnExportDOC().setOnAction(e -> {
            viewModel.exportToDoc("artworks.doc");
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
        view.getBtnShowStats().setOnAction(e -> {
            view.showStatistics(viewModel.getArtTypeStats());
        });
        view.getBtnFilter().setOnAction(e -> {
            boolean applied = false;

            String type = view.getFilterType();
            if (!type.isBlank()) {
                viewModel.filterByType(type);
                applied = true;
            }

            String minStr = view.getFilterPriceMin();
            String maxStr = view.getFilterPriceMax();
            if (!minStr.isBlank() && !maxStr.isBlank()) {
                try {
                    double min = Double.parseDouble(minStr);
                    double max = Double.parseDouble(maxStr);
                    viewModel.filterByPriceRange(min, max);
                    applied = true;
                } catch (NumberFormatException ex) {
                    System.err.println("Preț invalid.");
                }
            }
            String artistName = view.getFilterArtistName();
            if (!artistName.isBlank()) {
                viewModel.filterByArtistName(artistName);
                applied = true;
            }

//            String artistIdStr = view.getFilterArtistId();
//            if (!artistIdStr.isBlank()) {
//                try {
//                    int id = Integer.parseInt(artistIdStr);
//                    viewModel.filterByArtist(id);
//                    applied = true;
//                } catch (NumberFormatException ex) {
//                    System.err.println("ID artist invalid.");
//                }
//            }

            if (!applied) {
                viewModel.refresh(); // dacă nu s-a aplicat nimic, afișează tot
            }
        });


    }
}
