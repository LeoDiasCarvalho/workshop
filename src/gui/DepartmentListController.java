package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{
	
	private DepartmentService service;
	private ObservableList<Department> obsList;
	
	@FXML private TableView<Department> tableViewDepartment;
	@FXML private TableColumn<Department, Integer> tableColumnId;
	@FXML private TableColumn<Department, String> tableColumnName;
	@FXML private Button btnNew;
	
	@FXML public void onBtnNewAction(ActionEvent event) {
		
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	
	
	public void setService(DepartmentService service) {
		this.service = service;
	}


	private void initializeNodes() {
		
		//Inicia o comportamento das colunas da tabela
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//Pegando uma referencia da cena atual
		Stage stage = (Stage) Main.getMainScene().getWindow();
		
		//Setando a tabela para aparecer na janela inteira
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
		tableViewDepartment.prefWidthProperty().bind(stage.widthProperty());
		
	}
	
	public void updateTableView() {
		if(service == null) {
			
			gui.util.Alerts.showAlert("DepartmentList -> updateTableView","Service was null", null, AlertType.ERROR);
		}else {
			List<Department> list = service.findAll();
			obsList = FXCollections.observableArrayList(list);
			tableViewDepartment.setItems(obsList);
		}
	}
	
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartmentFormController controller = loader.getController();
			controller.setEntity(obj);
			controller.upDateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}
		catch(IOException e) {
			
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}

}
