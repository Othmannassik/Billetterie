module ma.emsi.billetterie {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.sql;


    opens ma.emsi.billetterie to javafx.fxml;
    exports ma.emsi.billetterie;
}