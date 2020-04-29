package InventorySystem.View_Controller;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/** @author Michael **/
/** Warning Pop-ups; button-triggered if conditions met. **/
public interface ButtonExceptions {
    
    default Optional<ButtonType> exitAlert() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Leaving IMS");
        exitAlert.setContentText("Are you sure you would like to exit?");
        Optional<ButtonType> exitConfirm = exitAlert.showAndWait();
        return exitConfirm;
    }
    
    default Optional<ButtonType> deleteAlert() {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Confirm delete");
        deleteAlert.setContentText("Are you sure you would like to delete the item?");
        Optional<ButtonType> deleteConfirm = deleteAlert.showAndWait();
        return deleteConfirm;
    }
    
    default Optional<ButtonType> cancelAlert() {
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("Cancel Add Product");
        cancelAlert.setContentText("Changes will be lost");
        Optional<ButtonType> cancelConfirm = cancelAlert.showAndWait();
        return cancelConfirm;
    }
    
    default void selectAlert() {
        Alert selectAlert = new Alert(Alert.AlertType.ERROR);
        selectAlert.setTitle("No item selected");
        selectAlert.setContentText("Please select an item from the table");
        selectAlert.showAndWait();
    }
        
    default void emptyAlert() {
        Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
        emptyAlert.setTitle("Required fields are missing");
        emptyAlert.setContentText("Please fill out all information and try again");
        emptyAlert.showAndWait();
    }
    
    default void boundsAlert() {
        Alert boundsAlert = new Alert(Alert.AlertType.ERROR);
        boundsAlert.setTitle("Stock count out of bounds");
        boundsAlert.setContentText("Please ensure stock amount is within minimum and maximum");
        boundsAlert.showAndWait();
    }
    
    default void notIntAlert() {
        Alert notIntAlert = new Alert(Alert.AlertType.ERROR);
        notIntAlert.setTitle("Invalid entry");
        notIntAlert.setContentText("Fields with asterisk (*) only accept numbers. Please try again");
        notIntAlert.showAndWait();
    }
}
