package com.example.artgallery.view;

import com.example.artgallery.model.ArtWork;
import com.example.artgallery.model.observer.Observer;
import com.example.artgallery.model.viewmodel.ArtWorkViewModel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
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
    private Button btnExportCSV = new Button("Export CSV");
    private Button btnExportDOC = new Button("Export DOC");
    private final Button btnShowStats = new Button("Show Statistics");

    private final TextField txtFilterType = new TextField();
    private final TextField txtFilterPriceMin = new TextField();
    private final TextField txtFilterPriceMax = new TextField();
    //private final TextField txtFilterArtist = new TextField();
    private final TextField txtFilterArtistName = new TextField();

    private final Button btnFilter = new Button("Filtrează");


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

        txtTitle.setPromptText(bundle.getString("label.title"));
        txtType.setPromptText(bundle.getString("label.type"));
        txtPrice.setPromptText(bundle.getString("label.price"));
        txtArtistId.setPromptText(bundle.getString("label.artistId"));
        txtSearch.setPromptText(bundle.getString("label.searchByTitle"));
        txtFilterType.setPromptText("Filtru: Tip");
        txtFilterPriceMin.setPromptText("Preț minim");
        txtFilterPriceMax.setPromptText("Preț maxim");
        //txtFilterArtist.setPromptText("ID Artist");
        txtFilterArtistName.setPromptText("Nume Artist");
        btnAdd.setText(bundle.getString("button.add"));
        btnUpdate.setText(bundle.getString("button.update"));
        btnDelete.setText(bundle.getString("button.delete"));

        TableColumn<ArtWork, String> titleCol = new TableColumn<>(bundle.getString("label.title"));
        titleCol.setCellValueFactory(c -> c.getValue().titleProperty());

        TableColumn<ArtWork, String> typeCol = new TableColumn<>(bundle.getString("label.type"));
        typeCol.setCellValueFactory(c -> c.getValue().typeProperty());

        TableColumn<ArtWork, Number> priceCol = new TableColumn<>(bundle.getString("label.price"));
        priceCol.setCellValueFactory(c -> c.getValue().priceProperty());

        table.getColumns().addAll(titleCol, typeCol, priceCol);

        this.getChildren().addAll(
                new Label(bundle.getString("label.title")), txtTitle,
                new Label(bundle.getString("label.type")), txtType,
                new Label(bundle.getString("label.price")), txtPrice,
                new Label(bundle.getString("label.artistId")), txtArtistId,
                new Label("Filtrare opere de artă:"),
                txtFilterType, txtFilterPriceMin, txtFilterPriceMax,txtFilterArtistName,
                //txtFilterArtist,
                btnFilter,
                txtSearch,
                btnAdd, btnUpdate, btnDelete,
                btnExportCSV, btnExportDOC,
                btnShowStats,
                table
        );
    }

    public void showStatistics(Map<String, Long> stats) {
        Stage stage = new Stage();
        stage.setTitle("Art Type Statistics");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Distribution of Art Types");

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
