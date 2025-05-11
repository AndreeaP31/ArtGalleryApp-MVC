package com.example.artgallery.view;

import com.example.artgallery.model.ArtWork;
import com.example.artgallery.model.observer.Observer;
import com.example.artgallery.model.viewmodel.ArtWorkViewModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.ResourceBundle;

public class ArtWorkView extends VBox implements Observer {
    private final ArtWorkViewModel viewModel;
    private final ResourceBundle bundle;

    private final TextField txtTitle = new TextField();
    private final TextField txtType = new TextField();
    private final TextField txtPrice = new TextField();
    private final TextField txtArtistId = new TextField();
    private final TextField txtSearch = new TextField();

    private final Button btnAdd = new Button();
    private final Button btnUpdate = new Button();
    private final Button btnDelete = new Button();
    private Button btnExportCSV = new Button();
    private Button btnExportDOC = new Button();
    private final Button btnShowStats = new Button();

    private final TextField txtFilterType = new TextField();
    private final TextField txtFilterPriceMin = new TextField();
    private final TextField txtFilterPriceMax = new TextField();
    //private final TextField txtFilterArtist = new TextField();
    private final TextField txtFilterArtistName = new TextField();

    private final Button btnFilter = new Button();

    private final TableView<ArtWork> table = new TableView<>();

    public ArtWorkView(ArtWorkViewModel viewModel, ResourceBundle bundle) {
        this.viewModel = viewModel;
        this.bundle = bundle;
        this.viewModel.addObserver(this);
        setupUI();
        setupBindings();
        update();
    }

    private void setupUI() {
        // Configure main layout
        this.setPadding(new Insets(15));
        this.setSpacing(10);

        // Create form grid for better alignment
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(5));

        // Configure form elements
        txtTitle.setPromptText(bundle.getString("label.title"));
        txtType.setPromptText(bundle.getString("label.type"));
        txtPrice.setPromptText(bundle.getString("label.price"));
        txtArtistId.setPromptText(bundle.getString("label.artistId"));
        txtSearch.setPromptText(bundle.getString("label.searchByTitle"));

        // Set up the form grid with labels and fields
        formGrid.add(new Label(bundle.getString("label.title")), 0, 0);
        formGrid.add(txtTitle, 1, 0);

        formGrid.add(new Label(bundle.getString("label.type")), 0, 1);
        formGrid.add(txtType, 1, 1);

        formGrid.add(new Label(bundle.getString("label.price")), 0, 2);
        formGrid.add(txtPrice, 1, 2);

        formGrid.add(new Label(bundle.getString("label.artistId")), 0, 3);
        formGrid.add(txtArtistId, 1, 3);

        // Configure form elements to grow properly
        GridPane.setHgrow(txtTitle, Priority.ALWAYS);
        GridPane.setHgrow(txtType, Priority.ALWAYS);
        GridPane.setHgrow(txtPrice, Priority.ALWAYS);
        GridPane.setHgrow(txtArtistId, Priority.ALWAYS);

        // Configure buttons
        btnAdd.setText(bundle.getString("button.add"));
        btnUpdate.setText(bundle.getString("button.update"));
        btnDelete.setText(bundle.getString("button.delete"));
        btnExportCSV.setText(bundle.getString("button.exportCSV"));
        btnExportDOC.setText(bundle.getString("button.exportDOC"));
        btnShowStats.setText(bundle.getString("button.showStats"));

        // Create button container for horizontal arrangement
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(btnAdd, btnUpdate, btnDelete, btnExportCSV, btnExportDOC, btnShowStats);

        // Set up filter section
        GridPane filterGrid = new GridPane();
        filterGrid.setHgap(10);
        filterGrid.setVgap(10);
        filterGrid.setPadding(new Insets(5));

        Label filterLabel = new Label(bundle.getString("label.filterTitle"));
        filterLabel.getStyleClass().add("filter-title");

        txtFilterType.setPromptText(bundle.getString("prompt.filterType"));
        txtFilterPriceMin.setPromptText(bundle.getString("prompt.filterPriceMin"));
        txtFilterPriceMax.setPromptText(bundle.getString("prompt.filterPriceMax"));
        //txtFilterArtist.setPromptText(bundle.getString("prompt.filterArtistId"));
        txtFilterArtistName.setPromptText(bundle.getString("prompt.filterArtistName"));
        btnFilter.setText(bundle.getString("button.filter"));

        filterGrid.add(txtFilterType, 0, 0);
        filterGrid.add(txtFilterPriceMin, 1, 0);
        filterGrid.add(txtFilterPriceMax, 2, 0);
        filterGrid.add(txtFilterArtistName, 3, 0);
        //filterGrid.add(txtFilterArtist, 4, 0);
        filterGrid.add(btnFilter, 4, 0);

        // Search section
        HBox searchBox = new HBox(10);
        HBox.setHgrow(txtSearch, Priority.ALWAYS);
        searchBox.getChildren().add(txtSearch);

        // Configure table columns
        TableColumn<ArtWork, String> titleCol = new TableColumn<>(bundle.getString("label.title"));
        titleCol.setCellValueFactory(c -> c.getValue().titleProperty());
        titleCol.setPrefWidth(200);

        TableColumn<ArtWork, String> typeCol = new TableColumn<>(bundle.getString("label.type"));
        typeCol.setCellValueFactory(c -> c.getValue().typeProperty());
        typeCol.setPrefWidth(150);

        TableColumn<ArtWork, Number> priceCol = new TableColumn<>(bundle.getString("label.price"));
        priceCol.setCellValueFactory(c -> c.getValue().priceProperty());
        priceCol.setPrefWidth(100);

        table.getColumns().addAll(titleCol, typeCol, priceCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Add all components to main layout
        this.getChildren().addAll(
                formGrid,
                buttonBox,
                filterLabel,
                filterGrid,
                searchBox,
                table
        );
    }

    public void showStatistics(Map<String, Long> stats) {
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("window.statsTitle"));

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(bundle.getString("chart.artTypeDistribution"));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        stats.forEach((type, count) -> series.getData().add(new XYChart.Data<>(type, count)));
        barChart.getData().add(series);

        VBox root = new VBox(barChart);
        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.show();
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

    public Button getBtnShowStats() {
        return btnShowStats;
    }

    public Button getBtnFilter() {
        return btnFilter;
    }

    public String getFilterType() {
        return txtFilterType.getText();
    }

    public String getFilterPriceMin() {
        return txtFilterPriceMin.getText();
    }

    public String getFilterPriceMax() {
        return txtFilterPriceMax.getText();
    }

//    public String getFilterArtistId() {
//        return txtFilterArtist.getText();
//    }

    public String getFilterArtistName() {
        return txtFilterArtistName.getText();
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public Button getBtnExportCSV() {
        return btnExportCSV;
    }

    public Button getBtnExportDOC() {
        return btnExportDOC;
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