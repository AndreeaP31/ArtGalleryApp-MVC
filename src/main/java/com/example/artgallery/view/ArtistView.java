package com.example.artgallery.view;

import com.example.artgallery.model.Artist;
import com.example.artgallery.model.observer.Observer;
import com.example.artgallery.model.viewmodel.ArtistViewModel;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
//txtFilterArtist,
import java.util.ResourceBundle;
//txtFilterArtist.setPromptText("ID Artist");
//private final TextField txtFilterArtist = new TextField(); in SetupUI
//    public String getFilterArtistId() {
//        return txtFilterArtist.getText();
//    }
public class ArtistView extends VBox implements Observer {
    private final ArtistViewModel viewModel;
    private final ResourceBundle bundle;

    private final TextField txtName = new TextField();
    private final DatePicker dpBirthDate = new DatePicker();
    private final TextField txtBirthPlace = new TextField();
    private final TextField txtNationality = new TextField();
    private final TextField txtSearch = new TextField();

    private final Button btnAdd = new Button();
    private final Button btnUpdate = new Button();
    private final Button btnDelete = new Button();

    private final TableView<Artist> table = new TableView<>();

    public ArtistView(ArtistViewModel viewModel, ResourceBundle bundle) {
        this.viewModel = viewModel;
        this.bundle = bundle;
        this.viewModel.addObserver(this);
        setupUI();
        setupBindings();
        update();
    }

    private void setupUI() {
        // Set spacing and padding for better layout
        this.setPadding(new Insets(15));
        this.setSpacing(10);

        // Create form grid for better alignment
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(5));

        // Configure text fields
        txtName.setPromptText(bundle.getString("label.name"));
        txtBirthPlace.setPromptText(bundle.getString("label.birthPlace"));
        txtNationality.setPromptText(bundle.getString("label.nationality"));
        txtSearch.setPromptText(bundle.getString("label.searchByName"));
        HBox.setHgrow(txtSearch, Priority.ALWAYS);

        // Set up the form grid
        formGrid.add(new Label(bundle.getString("label.name")), 0, 0);
        formGrid.add(txtName, 1, 0);

        formGrid.add(new Label(bundle.getString("label.birthDate")), 0, 1);
        formGrid.add(dpBirthDate, 1, 1);

        formGrid.add(new Label(bundle.getString("label.birthPlace")), 0, 2);
        formGrid.add(txtBirthPlace, 1, 2);

        formGrid.add(new Label(bundle.getString("label.nationality")), 0, 3);
        formGrid.add(txtNationality, 1, 3);

        // Configure form elements to grow properly
        GridPane.setHgrow(txtName, Priority.ALWAYS);
        GridPane.setHgrow(dpBirthDate, Priority.ALWAYS);
        GridPane.setHgrow(txtBirthPlace, Priority.ALWAYS);
        GridPane.setHgrow(txtNationality, Priority.ALWAYS);

        // Configure buttons
        btnAdd.setText(bundle.getString("button.add"));
        btnUpdate.setText(bundle.getString("button.update"));
        btnDelete.setText(bundle.getString("button.delete"));

        // Create button container for horizontal arrangement
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(btnAdd, btnUpdate, btnDelete);

        // Search section
        HBox searchBox = new HBox(10);
        searchBox.getChildren().add(txtSearch);

        // Configure table columns
        TableColumn<Artist, String> nameCol = new TableColumn<>(bundle.getString("label.name"));
        nameCol.setCellValueFactory(c -> c.getValue().nameProperty());
        nameCol.setPrefWidth(200);

        TableColumn<Artist, String> nationalityCol = new TableColumn<>(bundle.getString("label.nationality"));
        nationalityCol.setCellValueFactory(c -> c.getValue().nationalityProperty());
        nationalityCol.setPrefWidth(150);

        table.getColumns().addAll(nameCol, nationalityCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Add all components to main layout
        this.getChildren().addAll(
                formGrid,
                buttonBox,
                searchBox,
                table
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