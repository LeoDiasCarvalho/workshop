package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{
	
	private Department entity;
	
	@FXML private TextField txtId;
	@FXML private TextField txtName;
	@FXML private Label lblErrorName;
	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	
	@FXML public void onBtnSaveAction() {
		System.out.println("onBtnSaveAction");
	}
	
	@FXML public void onBtnCancelAction() {
		System.out.println("onBtnCancelAction");
	}
	
	public void upDateFormData() {
		if(entity == null) {
			throw new DbException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
	
	public void setEntity(Department entity) {
		this.entity = entity;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		initializeNodes();
		
	}
	
	public void initializeNodes() {
		
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

}
