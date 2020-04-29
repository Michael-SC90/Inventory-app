package InventorySystem.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
//Instantiate Singleton
    private final static Inventory INVENTORY = new Inventory();
    
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<Part> allParts = FXCollections.observableArrayList();
//Keep track of total number of items added so not to re-use ID's (unique indexes)
    private int partIDCounter = 0;
    private int prodIDCounter = 0;
    
//Return Inventory model
    public static Inventory getInstance() {
        return INVENTORY;
    }

//Instantitates Product object, opens Add Product window, and adds product object to products ArrayList
  public void addProduct(Product product) {
    products.add(product);
    ++prodIDCounter;
  }
  
//Removes product object from products ArrayList with indexed by productID
  public boolean deleteProduct(int productID) {
    return products.remove(lookupProduct(productID));
  }
  
//Finds product by productID within products ArrayList
  public Product lookupProduct(int productID) {
    int i = 0;
    while ((products.get(i).getProductID() != productID) & (i < products.size())) {
      i++;
    }
    if (products.get(i).getProductID() == productID) {
      return products.get(i);
    }
    else return null;
  }
  
//Re-assigns all fields with provided values (can be unchanged)
  public void updateProduct(int productID) {
    lookupProduct(productID);
  }
  
  public void updateProduct(int productID, Product product) {
      products.set(this.products.indexOf(lookupProduct(productID)), product);
  }
  
  public void addPart(Part part) {
    allParts.add(part);
    ++partIDCounter;
  }
  
//Removes Part sub-class object from allParts ArrayList using object reference
  public boolean deletePart(Part part) {
    return allParts.remove(part);
  }
  
//Finds part by partID (index) within allParts ArrayList
  public Part lookupPart(int partID) {
    int i = 0;
    while ((allParts.get(i).getPartID() != partID) & (i < allParts.size())) {
      i++;
    }
      if (allParts.get(i).getPartID() == partID) {
        Part part = allParts.get(i);
        if (part instanceof Inhouse){
          Inhouse inhouse = (Inhouse) allParts.get(i);
          return inhouse;
        } else if (part instanceof Outsourced) {
            Outsourced outsourced = (Outsourced) allParts.get(i);
            return outsourced;
          }
      }
    return null;
  }
  
/**
//Overridden to accept part parameter
  void updatePart(int partID) {
      //TODO
  } **/
  
//Re-assigns all fields with provided values (can be unchanged)
  public void updatePart(int partID, Part newPart) {
      allParts.set(this.allParts.indexOf(lookupPart(partID)), newPart);
  }
  
  public ObservableList getPartList() {
      return allParts;
  }
  
  public ObservableList getProductList() {
      return products;
  }
  
  public int getPartIndex(Part part) {
      int index = allParts.indexOf(part);
      return index;
  }
  
  public int getNewPartID() {
      return partIDCounter;
  }
  
  public int getNewProdID() {
      return prodIDCounter;
  }
  
  public void setPartIDCount(Inventory inventory) {
      this.partIDCounter = inventory.partIDCounter;
  }
  
  public void setProdIDCount(Inventory inventory) {
      this.prodIDCounter = inventory.prodIDCounter;
  }
  
  public void setPartList(Inventory inventory) {
      this.allParts.setAll(inventory.getPartList());
  }
  
  public void setProdList(Inventory inventory) {
      this.products.setAll(inventory.getProductList());
  }
}