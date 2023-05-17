module ma.emsi.billetterie {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.sql;


    opens ma.emsi.billetterie to javafx.fxml;
    opens ma.emsi.billetterie.entities to javafx.base;

    exports ma.emsi.billetterie;
}