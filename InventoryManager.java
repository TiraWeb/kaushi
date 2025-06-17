import java.util.ArrayList;
import java.util.List;

/**
 * InventoryManager class handles all inventory operations
 * Manages the ArrayList of Asset objects and provides CRUD operations
 */
public class InventoryManager {
    // ENCAPSULATION - private ArrayList to store assets
    private ArrayList<Asset> assets;
    
    /**
     * Constructor initializes the assets list with some sample data
     */
    public InventoryManager() {
        assets = new ArrayList<>();
        initializeSampleData();
    }
    
    /**
     * Initializes the inventory with sample data for demonstration
     * Shows POLYMORPHISM - storing different types of objects in same collection
     */
    private void initializeSampleData() {
        // Adding sample Electronics items
        assets.add(new Electronics("E001", "Laptop", "Computer Lab", 65.0));
        assets.add(new Electronics("E002", "Projector", "Classroom A", 250.0));
        assets.add(new Electronics("E003", "Printer", "Office", 45.0));
        
        // Adding sample Furniture items
        assets.add(new Furniture("F001", "Desk", "Classroom A", "Wood"));
        assets.add(new Furniture("F002", "Chair", "Library", "Plastic"));
        assets.add(new Furniture("F003", "Bookshelf", "Library", "Metal"));
    }
    
    /**
     * Adds a new asset to the inventory
     * @param asset The asset to add
     */
    public void addAsset(Asset asset) {
        assets.add(asset);
    }
    
    /**
     * Updates an existing asset in the inventory
     * @param index The index of the asset to update
     * @param updatedAsset The updated asset object
     */
    public void updateAsset(int index, Asset updatedAsset) {
        if (index >= 0 && index < assets.size()) {
            assets.set(index, updatedAsset);
        }
    }
    
    /**
     * Deletes an asset from the inventory
     * @param index The index of the asset to delete
     */
    public void deleteAsset(int index) {
        if (index >= 0 && index < assets.size()) {
            assets.remove(index);
        }
    }
    
    /**
     * Gets all assets in the inventory
     * @return ArrayList of all assets
     */
    public ArrayList<Asset> getAssets() {
        return assets;
    }
    
    /**
     * Gets an asset by index
     * @param index The index of the asset
     * @return The asset at the specified index, or null if invalid index
     */
    public Asset getAsset(int index) {
        if (index >= 0 && index < assets.size()) {
            return assets.get(index);
        }
        return null;
    }
    
    /**
     * Searches for assets by name (case-insensitive)
     * @param searchTerm The term to search for
     * @return List of assets matching the search term
     */
    public List<Asset> searchAssets(String searchTerm) {
        List<Asset> results = new ArrayList<>();
        String lowerSearchTerm = searchTerm.toLowerCase();
        
        for (Asset asset : assets) {
            if (asset.getName().toLowerCase().contains(lowerSearchTerm) ||
                asset.getAssetId().toLowerCase().contains(lowerSearchTerm) ||
                asset.getLocation().toLowerCase().contains(lowerSearchTerm)) {
                results.add(asset);
            }
        }
        return results;
    }
    
    /**
     * Checks if an asset ID already exists
     * @param assetId The ID to check
     * @return true if ID exists, false otherwise
     */
    public boolean assetIdExists(String assetId) {
        for (Asset asset : assets) {
            if (asset.getAssetId().equals(assetId)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Demonstrates POLYMORPHISM - calls overridden getDetails() method
     * on different types of objects stored in the same collection
     */
    public void printAllAssetDetails() {
        System.out.println("=== All Asset Details ===");
        for (Asset asset : assets) {
            // POLYMORPHISM in action - calls the appropriate getDetails() method
            // based on the actual object type (Electronics or Furniture)
            System.out.println(asset.getDetails());
        }
    }
}