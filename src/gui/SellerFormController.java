package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listener.DataChangedListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modal.exceptions.ValidationException;
import model.entities.Seller;
import model.services.SellerService;

public class SellerFormController implements Initializable{
	
	private Seller entity;
	private SellerService service;
	
	private List<DataChangedListener> dataChangedListener = new ArrayList<>();
	
	@FXML private TextField txtId;
	@FXML private TextField txtName;
	@FXML private Label lblErrorName;
	@FXML private Button btnSave;
	@FXML private Button btnCancel;
	
	@FXML public void onBtnSaveAction(ActionEvent event) {
		
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		try{
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			
			Utils.currentStage(event).close();
			
		}
		catch(ValidationException e) {
			setErrorMenssages(e.getErros());
		}
		catch(DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		
		for(DataChangedListener listener : dataChangedListener) {
			
			listener.onDataChanged();
		}
		
	}

	private Seller getFormData() {
		
		Seller obj = new Seller();
		
		ValidationException exception = new ValidationException("Validation Error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't by empty");
		}
		
		obj.setName(txtName.getText());
		
		if(exception.getErros().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML public void onBtnCancelAction(ActionEvent event) {
		
		Utils.currentStage(event).close();
	}
	
	public void upDateFormData() {
		if(entity == null) {
			throw new DbException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}
	
	
	public void setEntity(Seller entity) {
		this.entity = entity;
	}

	public void setService(SellerService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangedListener listener) {
		dataChangedListener.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		initializeNodes();
		
	}
	
	public void initializeNodes() {
		
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	private void setErrorMenssages(Map<String, String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name")) {
			lblErrorName.setText(errors.get("name"));
		}
	}

}
