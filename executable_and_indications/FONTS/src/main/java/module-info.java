module edu.upc.prop.clusterxx {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.upc.prop.clusterxx to javafx.fxml;
    exports edu.upc.prop.clusterxx;
    exports edu.upc.prop.clusterxx.domain;
    opens edu.upc.prop.clusterxx.domain to javafx.fxml;
    opens edu.upc.prop.clusterxx.visual to javafx.fxml;
    exports edu.upc.prop.clusterxx.visual;
    exports edu.upc.prop.clusterxx.data;
    opens edu.upc.prop.clusterxx.data to javafx.fxml;
}