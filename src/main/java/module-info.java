module com.example.artgallery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;

    opens com.example.artgallery to javafx.fxml;
    exports com.example.artgallery;

    exports com.example.artgallery.view;
}