/**
 * Abstract base class representing a generic asset in the school inventory.
 * Demonstrates ABSTRACTION - defines common properties and abstract method
 * that must be implemented by subclasses.
 */
public abstract class Asset {
    // ENCAPSULATION - all fields are private
    private String assetId;
    private String name;
    private String location;
    
    /**
     * Constructor for Asset
     * @param assetId Unique identifier for the asset
     * @param name Name of the asset
     * @param location Location where the asset is stored
     */
    public Asset(String assetId, String name, String location) {
        this.assetId = assetId;
        this.name = name;
        this.location = location;
    }
    
    // ENCAPSULATION - Public getters and setters for private fields
    public String getAssetId() {
        return assetId;
    }
    
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    /**
     * Abstract method that must be implemented by subclasses
     * Demonstrates ABSTRACTION - forces subclasses to provide specific implementation
     * @return String containing detailed information about the asset
     */
    public abstract String getDetails();
    
    /**
     * Returns the type of asset (to be overridden by subclasses)
     * @return String representing the asset type
     */
    public abstract String getAssetType();
}