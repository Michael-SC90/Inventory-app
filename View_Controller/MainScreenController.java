package InventorySystem.View_Controller;

import InventorySystem.Model.Inventory;
import InventorySystem.Model.LabelModel;
import InventorySystem.Model.Part;
import InventorySystem.Model.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
//import InventorySystem.Model.*;


/** @author Michael **/
public class MainScreenController implements Initializable, TableFilter, ButtonExceptions {

    Inventory inventory;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableView<Product> prodTable;
    @FXML
    private TableColumn<Part, String> partIDColumn, partNameColumn;
    @FXML
    private TableColumn<Product, String> prodIDColumn, prodNameColumn;
    @FXML
    private Button addPartBtn, modPartBtn, delPartBtn,
                   addProdBtn, modProdBtn, delProdBtn,
                   exitBtn;
    @FXML
    Label TitleLabel;
    @FXML
    TextField partSearchField, prodSearchField;
    private final LabelModel model = new LabelModel();
    

/** For testing table view
    private void addTestItems() {
        inventory.addPart(new Inhouse(inventory.getNewPartID(), "Stick", 10.00, 6, 6, 6, 666));
        inventory.addPart(new Inhouse(inventory.getNewPartID(), "Straw", 15.00, 5, 3, 6, 666));
        inventory.addPart(new Inhouse(inventory.getNewPartID(), "String", 20.00, 5, 3, 6, 666));
        inventory.addPart(new Outsourced(inventory.getNewPartID(), "Bucket", 15.00, 1, 3, 3, "hello"));
        inventory.addPart(new Outsourced(inventory.getNewPartID(), "Water", 15.00, 1, 3, 3, "hello"));
        inventory.addPart(new Outsourced(inventory.getNewPartID(), "Wheel", 15.00, 1, 3, 3, "hello"));
        inventory.addPart(new Outsourced(inventory.getNewPartID(), "Plank", 15.00, 1, 3, 3, "hello"));
        inventory.addProduct(new Product(inventory.getNewProdID(), "Cart", 10.00, 2, 1, 7));
        inventory.addProduct(new Product(inventory.getNewProdID(), "Broom", 5.50, 1, 1, 2));
        inventory.lookupProduct(0).addAssociatedPart(inventory.lookupPart(1));
        inventory.lookupProduct(0).addAssociatedPart(inventory.lookupPart(2));
        inventory.lookupProduct(0).addAssociatedPart(inventory.lookupPart(0));
    } **/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inventory = Inventory.getInstance();
        filterParts(partIDColumn, partNameColumn, partTable, partSearchField);
        filterProducts();
        //addTestItems();
    }

    private void filterProducts() {
        prodIDColumn.setCellValueFactory(cellData -> cellData.getValue().getProdIDProperty());
        prodNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProdNameProperty());
        FilteredList<Product> filteredProducts = new FilteredList<>(Inventory.getInstance().getProductList(), p -> true);
        prodSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProducts.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;    //returns value to Predicate, not method.
                }
                //Set newly typed chars to lower case and check if they're found; else index dne (-1)
                String lowerCaseFilter = newValue.toLowerCase();
                if (product.getProdName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                try {
                    if (("0000" + newValue).contains(product.getProdIDValue())) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace(); //Catches Char (not number)
                }
                return false;
            });
        });
        SortedList<Product> sortedParts = new SortedList<>(filteredProducts);
        sortedParts.comparatorProperty().bind(prodTable.comparatorProperty());
        prodTable.setItems(sortedParts);
    }
    
    private void partScreenHandler(String titleModifier) {
        try {
            model.setTitleText(titleModifier);
            Stage stage;
            Parent root;
            stage = (Stage) addPartBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PartScreen.fxml"));
            root = loader.load();
            PartScreenController controller = loader.getController();
            controller.setTitle(model);
            if (model.getTitleText().contains("Modify")) {
                Part part = partTable.getSelectionModel().getSelectedItem();
                controller.loadPart(part);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
    private void prodScreenHandler(String titleModifier) {
        try {
            model.setTitleText(titleModifier);
            Stage stage;
            Parent root;
            stage = (Stage) addProdBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProdScreen.fxml"));
            root = loader.load();
            ProdScreenController controller = loader.getController();
            controller.setTitle(model);
            if (model.getTitleText().contains("Modify")) {
                Product prod = prodTable.getSelectionModel().getSelectedItem();
                System.out.println(prod.getAssociatedPartList().size());
                controller.loadProduct(prod);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        try {
          if (event.getSource() == addPartBtn) {
            partScreenHandler("Add Part");
          }
          else if (event.getSource() == modPartBtn) {
            if (emptySelection(partTable)) {
              selectAlert();
            } else {
                partScreenHandler("Modify Part");
            }
          }
          else if (event.getSource() == delPartBtn) {
            if (emptySelection(partTable)) {
                selectAlert();
            } else if (deleteAlert().get() == ButtonType.OK) {
                    inventory.deletePart((Part) partTable.getSelectionModel().getSelectedItem());
                }
          }
          else if (event.getSource() == addProdBtn) {
            prodScreenHandler("Add Product");
          }
          else if (event.getSource() == modProdBtn) {
              if (emptySelection(prodTable)) {
                selectAlert();
            } else {
                  prodScreenHandler("Modify Product");
            }
          }
          else if (event.getSource() == delProdBtn) {
              if (emptySelection(prodTable)) {
                  selectAlert();
            } else if (deleteAlert().get() == ButtonType.OK) {
                    inventory.deleteProduct(prodTable.getSelectionModel().getSelectedItem().getProductID());
                }
          }
          else if (event.getSource() == exitBtn) {
            if (exitAlert().get() == ButtonType.OK) {
                Platform.exit();
            }
          }
      } catch (Exception e) {
          e.printStackTrace();
        }
    }
}