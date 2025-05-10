package com.example.artgallery.view;

import com.example.artgallery.model.ArtWork;
import com.example.artgallery.model.observer.Observer;
import com.example.artgallery.model.viewmodel.ArtWorkViewModel;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ArtWorkView extends VBox implements Observer {
    private final ArtWorkViewModel viewModel;

    private final TextField txtTitle = new TextField();
    private final TextField txtType = new TextField();
    private final TextField txtPrice = new TextField();
    private final TextField txtArtistId = new TextField();
    private final TextField txtSearch = new TextField();

    private final Button btnAdd = new Button("Add");
    private final Button btnUpdate = new Button("Update");
    private final Button btnDelete = new Button("Delete");

    private final TableView<ArtWork> table = new TableView<>();

    public ArtWorkView(ArtWorkViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
        setupUI();
        setupBindings();
        update();
    }

    private void setupUI() {
        txtTitle.setPromptText("Title");
        txtType.setPromptText("Type");
        txtPrice.setPromptText("Price");
        txtArtistId.setPromptText("Artist ID");
        txtSearch.setPromptText("Search by title...");

        TableColumn<ArtWork, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(c -> c.getValue().titleProperty());

        TableColumn<ArtWork, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(c -> c.getValue().typeProperty());

        TableColumn<ArtWork, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(c -> c.getValue().priceProperty());

        table.getColumns().addAll(titleCol, typeCol, priceCol);

        this.getChildren().addAll(
                txtSearch, txtTitle, txtType, txtPrice, txtArtistId,
                btnAdd, btnUpdate, btnDelete, table
        );
    }

    private void setupBindings() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtTitle.setText(newVal.getTitle());
                txtType.setText(newVal.getType());
                txtPrice.setText(String.valueOf(newVal.getPrice()));
                txtArtistId.setText(String.valueOf(newVal.getArtistId()));
            }
        });
    }

    public TextField getTxtSearch() {
        return txtSearch;
    }

    public Button getBtnAdd() {
        return btnAdd;
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public ArtWork getArtWorkFromForm() {
        ArtWork art = new ArtWork();
        art.setTitle(txtTitle.getText());
        art.setType(txtType.getText());
        try {
            art.setPrice(Double.parseDouble(txtPrice.getText()));
            art.setArtistId(Integer.parseInt(txtArtistId.getText()));
        } catch (NumberFormatException e) {
            System.err.println("Invalid price or artist ID input.");
        }
        return art;
    }

    public ArtWork getSelectedArtWork() {
        return table.getSelectionModel().getSelectedItem();
    }

    @Override
    public void update() {
        table.getItems().setAll(viewModel.getArtWorks());
    }
}
