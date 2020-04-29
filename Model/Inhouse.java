package InventorySystem.Model;

//Adds additional fields and methods for Inhouse Parts
public class Inhouse extends Part {
  private int machineID;
  
//Subclass constructor that invokes superclass constructor via super()
  public Inhouse(int partID, String partName, double price, int count, int countMin, int countMax, int internalID) {
      super(partID, partName, price, count, countMin, countMax);
      machineID = internalID;
  }

//Subclass constructor; does not require price
  public Inhouse(int partID, String partName, int count, int countMin, int countMax, int externalID) {
      super(partID, partName, count, countMin, countMax);
      machineID = externalID;
  }
  
//Subclass constructor; does not require company name
  public Inhouse(int partID, String partName, double price, int count, int countMin, int countMax) {
      super(partID, partName, price, count, countMin, countMax);
  }
  
//Subclass constructor; does not require either price nor company name
  public Inhouse(int partID, String partName, int count, int countMin, int countMax) {
      super(partID, partName, count, countMin, countMax);
  }
  
//Updates current object's machineID field with provided value
  public void setMachineID(int newMachineID) {
    this.machineID = newMachineID;
  }
  
//Provide current object's machineID
  public int getMachineID() {
    return this.machineID;
  }
}