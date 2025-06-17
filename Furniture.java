/**
 * Furniture class that extends Asset
 * Demonstrates INHERITANCE - inherits from Asset abstract class
 */
public class Furniture extends Asset {
    // ENCAPSULATION - private field specific to Furniture
    private String material;
    
    /**
     * Constructor for Furniture
     * @param assetId Unique identifier
     * @param name Name of the furniture item
     * @param location Location of the furniture
     * @param material Material the furniture is made from
     */
    public Furniture(String assetId, String name, String location, String material) {
        super(assetId, name, location); // Call parent constructor
        this.material = material;
    }
    
    // ENCAPSULATION - Getter and setter for material
    public String getMaterial() {
        return material;
    }
    
    public void setMaterial(String material) {
        this.material = material;
    }
    
    /**
     * POLYMORPHISM - Overrides abstract method from Asset class
     * Provides Furniture-specific implementation
     * @return Detailed string with furniture-specific information
     */
    @Override
    public String getDetails() {
        return String.format("Furniture: %s (ID: %s) - Location: %s, Material: %s", 
                           getName(), getAssetId(), getLocation(), material);
    }
    
    /**
     * POLYMORPHISM - Overrides method to return specific asset type
     * @return String indicating this is a Furniture asset
     */
    @Override
    public String getAssetType() {
        return "Furniture";
    }
    
    /**
     * Gets the specific property for table display
     * @return String representation of material
     */
    public String getSpecificProperty() {
        return material;
    }
}