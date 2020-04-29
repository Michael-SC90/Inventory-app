package InventorySystem.Model;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
  private final ObservableList<Part> associatedParts;
  private int partCount = 0;
  private int productID, inStock, min, max;
  private String name, formattedID;
  private double price;
  private final StringProperty prodNameProperty, prodIDProperty;
  
  public Product() {
      this.prodNameProperty = new SimpleStringProperty();
      this.prodIDProperty = new SimpleStringProperty();
      associatedParts = FXCollections.observableArrayList();
  }
  
  public Product(int prodID, String prodName, double prodPrice, int count, int prodMin, int prodMax) {
      productID = prodID;
      formattedID = String.format(Locale.US, "%0" + (6 - String.valueOf(prodID).length()) + "d", prodID);
      name = prodName;
      price = prodPrice;
      inStock = count;
      min = prodMin;
      max = prodMax;
      this.prodNameProperty = new SimpleStringProperty(prodName);
      this.prodIDProperty = new SimpleStringProperty(formattedID);
      associatedParts = FXCollections.observableArrayList();
  }
  
//Updates current object's productID field with provided value (auto-generated within Add Product window)
  public void setProductID(int newProductID) {
    this.productID = newProductID;
    int digits;
    digits = String.valueOf(newProductID).length();
    this.formattedID = String.format("%0" + (6 - digits) + "d", newProductID);
    this.prodIDProperty.set(formattedID);
  }

//Updates current object's name field with provided value
  public void setName(String partName) {
    this.name = partName;
    this.prodNameProperty.set(partName);
  }

//Updates current object's price field with provided value
  public void setPrice(double newPrice) {
    this.price = newPrice;
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

//Provide current object's productID value
  public int getProductID() {
    return this.productID;
  }
  
//Provide current object's name value
  public String getName() {
    return this.name;
  }

//Provide current object's price value
  public double getPrice() {
    return this.price;
  }

//Provide current object's inStock value
  public int getInStock() {
    return this.inStock;
  }
  
//Provide current object's min value
  public int getMin() {
    return this.min;
  }
  
//Provide current object's max value
  public int getMax() {
    return this.max;
  }
  
//Add provided part to allParts ArrayList
  public void addAssociatedPart(Part part) {
    this.associatedParts.add(part);
    this.partCount++;
  }
  
//Remove part object associated with provided partID from allParts ArrayList
  public boolean removeAssociatedPart(int partID) {
    if (this.associatedParts.remove(lookupAssociatedPart(partID))) {
      this.partCount--;
      return true;
    }
    else return false;
  }
  
//Provide part object associated with provided partID
  public Part lookupAssociatedPart(int partID) {
    int i = 0;
    while ((associatedParts.get(i).getPartID() != partID) & (i < associatedParts.size())) {
      i++;
    }
      if (associatedParts.get(i).getPartID() == partID) {
        Part part = associatedParts.get(i);
        if (part instanceof Inhouse){
          Inhouse inhouse = (Inhouse) associatedParts.get(i);
          return inhouse;
        } else if (part instanceof Outsourced) {
            Outsourced outsourced = (Outsourced) associatedParts.get(i);
            return outsourced;
          }
      }
    return null;
  }
  
  public String getFormattedID() {
      return this.formattedID;
  }
  
  public StringProperty getProdNameProperty() {
      return prodNameProperty;
  }
  
  public void setProdNameProperty(String prodName) {
      this.prodNameProperty.set(prodName);
  }
  
  public String getProdName() {
      return prodNameProperty.get();
  }
  
  public StringProperty getProdIDProperty() {
      return prodIDProperty;
  }
  
  public String getProdIDValue() {
      return prodIDProperty.get();
  }
    
  public ObservableList getAssociatedPartList() {
      return associatedParts;
  }
  
  public void setAssociatedPartList(Product product) {
      this.associatedParts.setAll(product.associatedParts);
  }
}