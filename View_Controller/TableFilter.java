package InventorySystem.View_Controller;

import InventorySystem.Model.Inventory;
import InventorySystem.Model.Part;
import InventorySystem.Model.Product;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/** @author Michael **/
public interface TableFilter {
    
    /** Search filter
     * @param column1
     * @param column2
     * @param table
     * @param searchField **/
    default void filterParts(TableColumn<Part, String> column1, TableColumn<Part, String> column2, TableView table, TextField searchField) {
    //Bind columns and table to update when textField for Part Search changes
        setPartColumnFactories(column1, column2);
    //Wrapping ObservableList into FilteredList (copy to allow truncations of list)
        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getInstance().getPartList(), p -> true); //Show ALL while p=true
    //If text field changes, check each part in filtered list to see if it should stay in list
        addPartSearchListener(searchField, filteredParts);
    //Convert FilteredList to SortedList; allows sorting before updating table
        SortedList<Part> sortedParts = new SortedList<>(filteredParts);
        sortedParts.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedParts);
    }
    
    /** Product included; views associatedParts. Overrides filteredParts list.
     * @param column1
     * @param column2
     * @param table
     * @param searchField
     * @param prod **/
    default void filterParts(TableColumn<Part, String> column1, TableColumn<Part, String> column2, TableView table, TextField searchField, Product prod) {
        setPartColumnFactories(column1, column2);
        FilteredList<Part> filteredParts = new FilteredList<>(prod.getAssociatedPartList(), p -> true);
        addPartSearchListener(searchField, filteredParts);
        SortedList<Part> sortedParts = new SortedList<>(filteredParts);
        sortedParts.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedParts);
    }
    
    default void setPartColumnFactories(TableColumn<Part, String> column1, TableColumn<Part, String> column2) {
        column1.setCellValueFactory(cellData -> cellData.getValue().getPartIDProperty());
        column2.setCellValueFactory(cellData -> cellData.getValue().getPartNameProperty());
    }
    
    /** Checks for changes to text fields and finds matching char entries in table. **/
    default void addPartSearchListener(TextField searchField, FilteredList<Part> tableList) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            tableList.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;    //returns value to Predicate, not method.
                }
                //Set newly typed chars to lower case and check if they're found; else index dne (-1)
                String lowerCaseFilter = newValue.toLowerCase();
                if (part.getPartName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                try {
                    if (("0000" + newValue).contains(part.getPartIDValue())) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace(); //Catches Char (not number)
                }
                return false;
            });
        });
    }
    
    default boolean emptySelection(TableView table) {
        return (table.getSelectionModel().getSelectedItem() == null);
    }
}