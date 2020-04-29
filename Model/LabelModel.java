package InventorySystem.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Michael
 */
public class LabelModel {
    private final StringProperty titleText = new SimpleStringProperty(" ");
    private final StringProperty subclassText = new SimpleStringProperty(" ");
    
    //textProperty method equivalent
    public StringProperty titleProperty() {
        return titleText;
    }
    
    //textProperty method equivalent
    public StringProperty subclassProperty() {
        return subclassText;
    }
    
    public final void setTitleText(String text) {
        titleProperty().set(text);
    }
    
    public final void setSubclassText(String text) {
        subclassProperty().set(text);
    }
    
    public final String getTitleText() {
        return titleProperty().get();
    }
    
    public final String getSubclassText() {
        return subclassProperty().get();
    }
}