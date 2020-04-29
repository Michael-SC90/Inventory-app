package InventorySystem.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import InventorySystem.Model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** @author Michael **/
public class ProdScreenController implements Initializable, TableFilter, ButtonExceptions {
    
    private Inventory inventory;
    private LabelModel model;
    private Product newProd;
    @FXML
    private TableView<Part> allPartsTable;
    @FXML
    private TableView<Part> addedPartsTable;
    @FXML
    private TableColumn<Part, String> allIDColumn;
    @FXML
    private TableColumn<Part, String> allNameColumn;
    @FXML
    private TableColumn<Part, String> addedIDColumn;
    @FXML
    private TableColumn<Part, String> addedNameColumn;
    @FXML
    Label titleLabel;
    @FXML
    private Button addPart, deletePart, saveProd, cnclProd;
    @FXML
    private TextField prodID, prodName, inStock, cost, prodMin, prodMax, searchAllParts, searchAddedParts;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inventory = Inventory.getInstance();
        newProd = new Product();
        filterParts(allIDColumn, allNameColumn, allPartsTable, searchAllParts);
        filterParts(addedIDColumn, addedNameColumn, addedPartsTable, searchAddedParts, newProd);
    }
    
    public void setTitle(LabelModel model) {
        titleLabel.textProperty().unbind();
        this.model = model;
        titleLabel.textProperty().bind(model.titleProperty());
    }
    
//Loads product data into text fields; used for modifying products
    public void loadProduct(Product prod) {
        prodID.setText(String.valueOf(prod.getProductID()));
        prodName.setText(prod.getName());
        inStock.setText(String.valueOf(prod.getInStock()));
        cost.setText(String.valueOf(prod.getPrice()));
        prodMin.setText(String.valueOf(prod.getMin()));
        prodMax.setText(String.valueOf(prod.getMax()));
        newProd.getAssociatedPartList().setAll(prod.getAssociatedPartList());
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
            stage = (Stage) saveProd.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == addPart) {
            if (allPartsTable.getSelectionModel().getSelectedItem() == null) {
                selectAlert();
            } else {
                newProd.addAssociatedPart(allPartsTable.getSelectionModel().getSelectedItem());
            }
        } else if (event.getSource() == deletePart) {
            if (addedPartsTable.getSelectionModel().getSelectedItem() == null) {
                selectAlert();
            } else if (deleteAlert().get() == ButtonType.OK) {
                newProd.removeAssociatedPart(addedPartsTable.getSelectionModel().getSelectedItem().getPartID());
            }
        } else if (event.getSource() == saveProd) {
            if ((checkRequiredFields(prodName, cost, inStock, prodMin, prodMax))) {
                if (prodID.textProperty().get().matches("Auto-generated")) {
                    newProd.setProductID(inventory.getNewProdID());
                    newProd.setName(prodName.getText());
                    newProd.setPrice(Double.parseDouble(cost.getText()));
                    newProd.setInStock(Integer.parseInt(inStock.getText()));
                    newProd.setMin(Integer.parseInt(prodMin.getText()));
                    newProd.setMax(Integer.parseInt(prodMax.getText()));
                    inventory.addProduct(newProd);
                } else {
                    newProd.setProductID(Integer.parseInt(prodID.getText()));
                    if (newProd.getName() != prodName.getText()) {
                        newProd.setName(prodName.getText());
                    }
                    try {
                        if (newProd.getPrice() != Double.parseDouble(cost.getText())) {
                            newProd.setPrice(Double.parseDouble(cost.getText()));
                        }
                    } catch (Exception e) {
                        newProd.setPrice(0); //Zero if null; placeholder
                    }
                    if (newProd.getInStock() != Integer.parseInt(inStock.getText())) {
                        newProd.setInStock(Integer.parseInt(inStock.getText()));
                    }
                    if (newProd.getMin() != Integer.parseInt(prodMin.getText())) {
                        newProd.setMin(Integer.parseInt(prodMin.getText()));
                    }
                    if (newProd.getMax() != Integer.parseInt(prodMax.getText())) {
                        newProd.setMax(Integer.parseInt(prodMax.getText()));
                    }
                    if (!newProd.getAssociatedPartList().equals(inventory.lookupProduct(newProd.getProductID()))) {
                        inventory.lookupProduct(newProd.getProductID()).getAssociatedPartList().setAll(newProd.getAssociatedPartList());
                    }
                    inventory.updateProduct(newProd.getProductID(), newProd);
                }
                returnToMain();
            }
        } else if (event.getSource() == cnclProd) {
                System.out.println("Cancel has been clicked");
                if (cancelAlert().get() == ButtonType.OK) {
                    returnToMain();
                }
          }
    }
}