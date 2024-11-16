module edu.upc.prop.clusterxx {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.upc.prop.clusterxx to javafx.fxml;
    exports edu.upc.prop.clusterxx;
}