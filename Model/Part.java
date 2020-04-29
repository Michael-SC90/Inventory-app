package InventorySystem.Model;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Parts are extended by Outsourced and Inhouse classes; can only be instantiated as subclasses
public abstract class Part {
  private int itemID, inStock, min, max;
  private String name, formattedID;
  private double partPrice;
  private final StringProperty partNameProperty, partIDProperty;
  
//Constructor for subclasses to instantiate via super() method
  public Part(int partID, String partName, double price, int count, int countMin, int countMax) {
      itemID = partID;
      formattedID = String.format(Locale.US, "%0" + (6 - String.valueOf(partID).length()) + "d", partID);
      name = partName;
      partPrice = price;
      inStock = count;
      min = countMin;
      max = countMax;
      this.partNameProperty = new SimpleStringProperty(partName);
      this.partIDProperty = new SimpleStringProperty(formattedID);
  }
  
//Constructor for subclasses to instantiate via super(); does not require price
  public Part(int partID, String partName, int count, int countMin, int countMax) {
      itemID = partID;
      formattedID = String.format(Locale.US, "%0" + (6 - String.valueOf(partID).length()) + "d", partID);
      name = partName;
      inStock = count;
      min = countMin;
      max = countMax;
      this.partNameProperty = new SimpleStringProperty(partName);
      this.partIDProperty = new SimpleStringProperty(formattedID);
  }
  
//Updates current object's partID field with provided value (auto-generated within Add Part window)
  public void setItemID(int newPartID) {
    this.itemID = newPartID;
    int digits;
    digits = String.valueOf(newPartID).length();
    this.formattedID = String.format("%0" + (6 - digits) + "d", newPartID);
    this.partIDProperty.set(formattedID);
  }
  
//Updates current object's name field with provided value
  public void setName(String newName) {
    this.name = newName;
  }
  
//Updates current object's price field with provided value
  public void setPrice(double newPrice) {
    this.partPrice = newPrice;
  }
  
//Updates current object's inStock field with provided value
  public void setInStock(int count) {
      this.inStock = count;
  }
  
//Updates current object's min field with provided value
  public void setMin(int newMin) {
      this.min = newMin;
  }
  
//Updates current object's max field with provided value
  public void setMax(int newMax) {
      this.max = newMax;
  }
  
//Provide current object's max value
  public int getPartID() {
    return this.itemID;
  }
  
  public String getName() {
    return this.name;
  }
  
  public double getPrice() {
    return this.partPrice;
  }
  
  public int getInStock() {
    return this.inStock;
  }
  
  public int getMin() {
    return this.min;
  }
  
//Provide current object's max value
  public int getMax() {
    return this.max;
  }
  
  public void setFormattedID(int partID) {
      int digits;
      digits = String.valueOf(partID).length();
      this.formattedID = String.format("%0" + (5 - digits) + "d", partID);
  }
  
  public String getFormattedID() {
      return this.formattedID;
  }
  
  public void setPartNameProperty(String partName) {
      this.partNameProperty.set(partName);
  }
  
  public StringProperty getPartNameProperty() {
      return partNameProperty;
  }
  
  public String getPartName() {
      return partNameProperty.get();
  }
  
  public StringProperty getPartIDProperty() {
      return partIDProperty;
  }
  
  public String getPartIDValue() {
      return partIDProperty.get();
  }
}