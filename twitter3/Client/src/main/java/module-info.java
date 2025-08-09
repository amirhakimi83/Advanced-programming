module com.example.client {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires java.sql.rowset;


	opens com.example.client to javafx.fxml;
	exports com.example.client;
}