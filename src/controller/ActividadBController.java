package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Persona;

public class ActividadBController implements Initializable {

    @FXML
    private Button btnAgregarPersona;

    @FXML
    private TableColumn<Persona, String> columnaApellidos;

    @FXML
    private TableColumn<Persona, Integer> columnaEdad;

    @FXML
    private TableColumn<Persona, String> columnaNombre;

    @FXML
    private TableView<Persona> tablaPersonas;

    @FXML
    private TextField tfApellidos;

    @FXML
    private TextField tfEdad;

    @FXML
    private TextField tfNombre;
    

    @FXML
    void agregarPersona(ActionEvent event) {
    	String nombre = tfNombre.getText() != null ? tfNombre.getText().toString() : "";
    	String apellidos = tfApellidos.getText() != null ? tfApellidos.getText().toString() : "";
    	int edad = tfEdad.getText() != null ? parseInt(tfEdad.getText().toString()) : -1;
    	Persona persona = new Persona(nombre, apellidos, edad);
    	if (validarPersona(persona)) {    		
    		insertarPersona(persona);
    	}
    	
    	
    }
    
    private void insertarPersona(Persona persona) {
    	ObservableList<Persona> personas = tablaPersonas.getItems();
    	
    	if (!personas.contains(persona)) {
    		tablaPersonas.getItems().add(persona);
    		tablaPersonas.refresh();
    		Alert alert = new Alert(AlertType.INFORMATION, "Persona añadida correctamente", ButtonType.OK);
    		alert.showAndWait();
    	} else {
    		Alert alert = new Alert(AlertType.INFORMATION, "Persona añadida correctamente", ButtonType.OK);
    		alert.showAndWait();
    	}
    	
	}

    
    private boolean validarPersona(Persona persona) {
    	StringBuilder sb = new StringBuilder();
    	
    	if (persona.getNombre() == null || persona.getNombre().isBlank()) {
    		sb.append("El campo Nombre es obligatorio\n");
    	}
    	if (persona.getApellidos() == null || persona.getApellidos().isBlank()) {
    		sb.append("El campo Apellidos es obligatorio\n");    		
    	}
    	if (persona.getEdad() < 0) {
    		sb.append("El campo Edad es obligatorio\n");
    	}
    	
    	if (!sb.isEmpty()) {
    		Alert alert = new Alert(AlertType.ERROR, sb.toString(), ButtonType.OK);
    		alert.showAndWait();
    		return false;
    	}
    	return true;
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		columnaApellidos.setCellValueFactory(param -> {
			Persona persona = param.getValue();
			if (persona != null && persona.getApellidos() != null) {					
				return new SimpleStringProperty(persona.getApellidos());
			}
			return new SimpleStringProperty("");
		});
		
		columnaNombre.setCellValueFactory(param -> {
			Persona persona = param.getValue();
			if (persona != null && persona.getNombre() != null) {					
				return new SimpleStringProperty(persona.getNombre());
			}
			return new SimpleStringProperty("");
		});
		
		columnaEdad.setCellValueFactory(param -> {
			Persona persona = param.getValue();
			if (persona.getEdad() >= 0) {
				return new SimpleIntegerProperty(persona.getEdad()).asObject();
			}
			return  new SimpleIntegerProperty().asObject();
		});
	
	}

	
	private static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return -1;
		} catch (NullPointerException e1) {
			return -1;
		}
	}
}
