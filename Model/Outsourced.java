package InventorySystem.Model;

//Adds additional fields and methods for Outsourced Parts
public class Outsourced extends Part {
  private String companyName;

//Subclass constructor that invokes superclass constructor via super()
  public Outsourced(int partID, String partName, double price, int count, int countMin, int countMax, String externalID) {
      super(partID, partName, price, count, countMin, countMax);
      companyName = externalID;
  }
  
//Subclass constructor; does not require price
  public Outsourced(int partID, String partName, int count, int countMin, int countMax, String externalID) {
      super(partID, partName, count, countMin, countMax);
      companyName = externalID;
  }
  
//Subclass constructor; does not require company name
  public Outsourced(int partID, String partName, double price, int count, int countMin, int countMax) {
      super(partID, partName, price, count, countMin, countMax);
  }
  
//Subclass constructor; does not require either price nor company name
  public Outsourced(int partID, String partName, int count, int countMin, int countMax) {
      super(partID, partName, count, countMin, countMax);
  }
  
//Updates current object's companyName field with provided value
  public void setCompanyName(String newCompanyName) {
    this.companyName = newCompanyName;
  }

//Provide current object's companyName
  public String getCompanyName() {
    return this.companyName;
  }
}