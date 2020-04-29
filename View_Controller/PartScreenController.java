package InventorySystem.View_Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import InventorySystem.Model.Part;
import InventorySystem.Model.Inhouse;
import InventorySystem.Model.Inventory;
import InventorySystem.Model.Outsourced;
import InventorySystem.Model.LabelModel;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Michael
 */
public class PartScreenController implements Initializable, ButtonExceptions {

    private Inventory inventory;
    private LabelModel model;
    @FXML
    Label titleLabel, subclassLabel;
    @FXML
    private Button savePart, cnclAddPart;
    @FXML
    RadioButton inhouseRadio, outsourcedRadio;
    @FXML
    ToggleGroup partType;
    @FXML
    private TextField partID, partName, inStock, cost, partMin, partMax, subclassField;
    
    /** Radio buttons change label for final text field **/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inventory = Inventory.getInstance();
        partType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (partType.getSelectedToggle() != null) {
                    if (partType.getSelectedToggle() == inhouseRadio) {
                        setSubclass("Machine ID", model);
                    }
                    else if (partType.getSelectedToggle() == outsourcedRadio) {
                        setSubclass("Company", model);
                    }
                }
            }
        });
    }
    
    /** Changes title depending on whether Add or Modify button was clicked on Main Screen **/
    public void setTitle(LabelModel model) {
        titleLabel.textProperty().unbind();
        this.model = model;
        titleLabel.textProperty().bind(model.titleProperty());
    }
    
    //Changes subclassLabel; receives string based on which radio button is selected
    public void setSubclass(String string, LabelModel model) {
        subclassLabel.textProperty().unbind();
        this.model = model;
        model.setSubclassText(string);
        subclassLabel.textProperty().bind(model.subclassProperty());
    }
    
    //Loads part data into text fields; used for modifying parts
    public void loadPart(Part part) {
        if (part instanceof Inhouse){
          Inhouse loaded = (Inhouse) part;
          subclassField.setText(String.valueOf(loaded.getMachineID()));
          inhouseRadio.selectedProperty().set(true);
        } else if (part instanceof Outsourced) {
            Outsourced loaded = (Outsourced) part;
            subclassField.setText(loaded.getCompanyName());
            outsourcedRadio.selectedProperty().set(true);
          }
        partID.setText(String.valueOf(part.getPartID()));
        partName.setText(part.getName());
        inStock.setText(String.valueOf(part.getInStock()));
        cost.setText(String.valueOf(part.getPrice()));
        partMin.setText(String.valueOf(part.getMin()));
        partMax.setText(String.valueOf(part.getMax()));
    }
    
    //Checks that primary fields are not empty, then checks bounds condition between stock count and min/max
    private boolean checkRequiredFields(TextField name, TextField cost, TextField count, TextField min, TextField max) {
        int low, mid, high;
        if (name.getText().isEmpty() | cost.getText().isEmpty() | count.getText().isEmpty() | min.getText().isEmpty() | max.getText().isEmpty()) {
            emptyAlert();
            return false;
        }
        low = Integer.parseInt(min.getText());
        mid = Integer.parseInt(count.getText());
        high = Integer.parseInt(max.getText());
        if (low > mid | high < mid) {
            boundsAlert();
            return false;
        } else return true;
    }
    
    private void returnToMain() {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) cnclAddPart.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Method linked to buttons; saves part or cancels and returns to main screen
    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == savePart) {
            if (checkRequiredFields(partName, cost, inStock, partMin, partMax)) {
                int partNum;
                if (partID.textProperty().get().matches("Auto-generated")) {
                    partNum = inventory.getNewPartID(); //New part with new part ID
                } else {partNum = Integer.parseInt(partID.getText());} //Existing parts retain part ID
                if (partType.getSelectedToggle() == inhouseRadio) {
                    Inhouse newPart = new Inhouse(
                            partNum,
                            partName.getText(),
                            Double.parseDouble(cost.getText()),
                            Integer.parseInt(inStock.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            Integer.parseInt(subclassField.getText()));
                    if (partID.textProperty().get().matches("Auto-generated")) {
                        inventory.addPart(newPart);
                    } else {inventory.updatePart(partNum, newPart);}
                } else if (partType.getSelectedToggle() == outsourcedRadio) {
                    Outsourced newPart = new Outsourced(
                            partNum,
                            partName.getText(),
                            Double.parseDouble(cost.getText()),
                            Integer.parseInt(inStock.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            subclassField.getText());
                    if (partID.textProperty().get().matches("Auto-generated")) {
                        inventory.addPart(newPart);
                    } else {inventory.updatePart(partNum, newPart);}
                }
            returnToMain();
            }
        } else if (event.getSource() == cnclAddPart) {
            if (cancelAlert().get() == ButtonType.OK) {
                returnToMain();
            }
          }
    }
}