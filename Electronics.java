/**
 * Electronics class that extends Asset
 * Demonstrates INHERITANCE - inherits from Asset abstract class
 */
public class Electronics extends Asset {
    // ENCAPSULATION - private field specific to Electronics
    private double wattage;
    
    /**
     * Constructor for Electronics
     * @param assetId Unique identifier
     * @param name Name of the electronic device
     * @param location Location of the device
     * @param wattage Power consumption in watts
     */
    public Electronics(String assetId, String name, String location, double wattage) {
        super(assetId, name, location); // Call parent constructor
        this.wattage = wattage;
    }
    
    // ENCAPSULATION - Getter and setter for wattage
    public double getWattage() {
        return wattage;
    }
    
    public void setWattage(double wattage) {
        this.wattage = wattage;
    }
    
    /**
     * POLYMORPHISM - Overrides abstract method from Asset class
     * Provides Electronics-specific implementation
     * @return Detailed string with electronics-specific information
     */
    @Override
    public String getDetails() {
        return String.format("Electronics: %s (ID: %s) - Location: %s, Wattage: %.1fW", 
                           getName(), getAssetId(), getLocation(), wattage);
    }
    
    /**
     * POLYMORPHISM - Overrides method to return specific asset type
     * @return String indicating this is an Electronics asset
     */
    @Override
    public String getAssetType() {
        return "Electronics";
    }
    
    /**
     * Gets the specific property for table display
     * @return String representation of wattage
     */
    public String getSpecificProperty() {
        return wattage + "W";
    }
}