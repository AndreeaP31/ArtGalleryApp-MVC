package com.example.artgallery.view;

import com.example.artgallery.model.Artist;
import com.example.artgallery.model.observer.Observer;
import com.example.artgallery.model.viewmodel.ArtistViewModel;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ArtistView extends VBox implements Observer {
    private final ArtistViewModel viewModel;

    private final TextField txtName = new TextField();
    private final DatePicker dpBirthDate = new DatePicker();
    private final TextField txtBirthPlace = new TextField();
    private final TextField txtNationality = new TextField();
    private final TextField txtSearch = new TextField();

    private final Button btnAdd = new Button("Add");
    private final Button btnUpdate = new Button("Update");
    private final Button btnDelete = new Button("Delete");

    private final TableView<Artist> table = new TableView<>();

    public ArtistView(ArtistViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
        setupUI();
        setupBindings();
        update();
    }

    private void setupUI() {
        txtName.setPromptText("Name");
        txtBirthPlace.setPromptText("Birth Place");
        txtNationality.setPromptText("Nationality");
        txtSearch.setPromptText("Search by name...");

        TableColumn<Artist, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(c -> c.getValue().nameProperty());

        TableColumn<Artist, String> nationalityCol = new TableColumn<>("Nationality");
        nationalityCol.setCellValueFactory(c -> c.getValue().nationalityProperty());

        table.getColumns().addAll(nameCol, nationalityCol);

        this.getChildren().addAll(
                txtSearch, txtName, dpBirthDate, txtBirthPlace, txtNationality,
                btnAdd, btnUpdate, btnDelete, table
        );
    }

    private void setupBindings() {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtName.setText(newVal.getName());
                dpBirthDate.setValue(newVal.getBirthDate());
                txtBirthPlace.setText(newVal.getBirthPlace());
                txtNationality.setText(newVal.getNationality());
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

    public Artist getArtistFromForm() {
        Artist artist = new Artist();
        artist.setName(txtName.getText());
        artist.setBirthDate(dpBirthDate.getValue());
        artist.setBirthPlace(txtBirthPlace.getText());
        artist.setNationality(txtNationality.getText());
        return artist;
    }

    public Artist getSelectedArtist() {
        return table.getSelectionModel().getSelectedItem();
    }

    @Override
    public void update() {
        table.getItems().setAll(viewModel.getArtistList());
    }
}
