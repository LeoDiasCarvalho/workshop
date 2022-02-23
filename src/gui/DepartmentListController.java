package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	@FXML public void onBtnNewAction() {
		System.out.println("onBtnNewAction");
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

}
