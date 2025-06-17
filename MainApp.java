import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MainApp class - Contains the main method and GUI implementation
 * Uses Java Swing for the graphical user interface
 */
public class MainApp extends JFrame {
    // GUI Components
    private InventoryManager inventoryManager;
    private JTable assetTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, searchButton;
    private JTextField searchField;
    
    // Table column names
    private final String[] columnNames = {"ID", "Name", "Type", "Location", "Specifics"};
    
    /**
     * Constructor - Sets up the main application window
     */
    public MainApp() {
        inventoryManager = new InventoryManager();
        initializeGUI();
        loadTableData();
    }
    
    /**
     * Initializes the GUI components and layout
     */
    private void initializeGUI() {
        setTitle("School Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create table model and table
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        assetTable = new JTable(tableModel);
        assetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Create scroll pane for table
        JScrollPane scrollPane = new JScrollPane(assetTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        
        // Create search panel
        JPanel searchPanel = createSearchPanel();
        
        // Add components to main frame
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set frame properties
        pack();
        setLocationRelativeTo(null); // Center the window
        setResizable(true);
    }
    
    /**
     * Creates the button panel with CRUD operation buttons
     * @return JPanel containing the buttons
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        addButton = new JButton("Add Asset");
        editButton = new JButton("Edit Selected Asset");
        deleteButton = new JButton("Delete Selected Asset");
        
        // Add action listeners
        addButton.addActionListener(e -> showAddEditDialog(false, -1));
        editButton.addActionListener(e -> editSelectedAsset());
        deleteButton.addActionListener(e -> deleteSelectedAsset());
        
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        
        return panel;
    }
    
    /**
     * Creates the search panel
     * @return JPanel containing search components
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        panel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        JButton showAllButton = new JButton("Show All");
        
        searchButton.addActionListener(e -> performSearch());
        showAllButton.addActionListener(e -> loadTableData());
        
        // Allow Enter key to trigger search
        searchField.addActionListener(e -> performSearch());
        
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(showAllButton);
        
        return panel;
    }
    
    /**
     * Loads all asset data into the table
     * Demonstrates POLYMORPHISM - different asset types displayed uniformly
     */
    private void loadTableData() {
        tableModel.setRowCount(0); // Clear existing data
        
        for (Asset asset : inventoryManager.getAssets()) {
            Object[] rowData = {
                asset.getAssetId(),
                asset.getName(),
                asset.getAssetType(), // POLYMORPHISM - calls overridden method
                asset.getLocation(),
                getSpecificProperty(asset) // POLYMORPHISM - gets type-specific property
            };
            tableModel.addRow(rowData);
        }
    }
    
    /**
     * Gets the specific property for display based on asset type
     * Demonstrates POLYMORPHISM - different behavior based on object type
     * @param asset The asset to get the property from
     * @return String representation of the specific property
     */
    private String getSpecificProperty(Asset asset) {
        if (asset instanceof Electronics) {
            return ((Electronics) asset).getSpecificProperty();
        } else if (asset instanceof Furniture) {
            return ((Furniture) asset).getSpecificProperty();
        }
        return "";
    }
    
    /**
     * Performs search operation and updates table with results
     */
    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadTableData();
            return;
        }
        
        tableModel.setRowCount(0);
        var searchResults = inventoryManager.searchAssets(searchTerm);
        
        for (Asset asset : searchResults) {
            Object[] rowData = {
                asset.getAssetId(),
                asset.getName(),
                asset.getAssetType(),
                asset.getLocation(),
                getSpecificProperty(asset)
            };
            tableModel.addRow(rowData);
        }
        
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No assets found matching: " + searchTerm, 
                "Search Results", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Handles editing of selected asset
     */
    private void editSelectedAsset() {
        int selectedRow = assetTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an asset to edit.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        showAddEditDialog(true, selectedRow);
    }
    
    /**
     * Handles deletion of selected asset
     */
    private void deleteSelectedAsset() {
        int selectedRow = assetTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an asset to delete.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this asset?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            inventoryManager.deleteAsset(selectedRow);
            loadTableData();
            JOptionPane.showMessageDialog(this, 
                "Asset deleted successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Shows the Add/Edit dialog window
     * @param isEdit true if editing, false if adding
     * @param selectedRow the row index if editing, -1 if adding
     */
    private void showAddEditDialog(boolean isEdit, int selectedRow) {
        JDialog dialog = new JDialog(this, isEdit ? "Edit Asset" : "Add Asset", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Input fields
        JTextField idField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField locationField = new JTextField(15);
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Electronics", "Furniture"});
        JTextField specificField = new JTextField(15);
        JLabel specificLabel = new JLabel("Wattage:");
        
        // If editing, populate fields with existing data
        if (isEdit) {
            Asset asset = inventoryManager.getAsset(selectedRow);
            idField.setText(asset.getAssetId());
            nameField.setText(asset.getName());
            locationField.setText(asset.getLocation());
            
            if (asset instanceof Electronics) {
                typeCombo.setSelectedItem("Electronics");
                specificField.setText(String.valueOf(((Electronics) asset).getWattage()));
                specificLabel.setText("Wattage:");
            } else if (asset instanceof Furniture) {
                typeCombo.setSelectedItem("Furniture");
                specificField.setText(((Furniture) asset).getMaterial());
                specificLabel.setText("Material:");
            }
        }
        
        // Type combo box listener to change specific field label
        typeCombo.addActionListener(e -> {
            String selectedType = (String) typeCombo.getSelectedItem();
            if ("Electronics".equals(selectedType)) {
                specificLabel.setText("Wattage:");
                if (!isEdit) specificField.setText("");
            } else {
                specificLabel.setText("Material:");
                if (!isEdit) specificField.setText("");
            }
        });
        
        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        dialog.add(new JLabel("Asset ID:"), gbc);
        gbc.gridx = 1;
        dialog.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        dialog.add(typeCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Location:"), gbc);
        gbc.gridx = 1;
        dialog.add(locationField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(specificLabel, gbc);
        gbc.gridx = 1;
        dialog.add(specificField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton(isEdit ? "Update" : "Add");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            if (validateAndSaveAsset(dialog, idField, nameField, locationField, 
                                   typeCombo, specificField, isEdit, selectedRow)) {
                dialog.dispose();
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    /**
     * Validates input and saves the asset
     * @return true if successful, false if validation failed
     */
    private boolean validateAndSaveAsset(JDialog dialog, JTextField idField, 
                                       JTextField nameField, JTextField locationField,
                                       JComboBox<String> typeCombo, JTextField specificField,
                                       boolean isEdit, int selectedRow) {
        try {
            // Validate required fields
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            String type = (String) typeCombo.getSelectedItem();
            String specificValue = specificField.getText().trim();
            
            if (id.isEmpty() || name.isEmpty() || location.isEmpty() || specificValue.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "All fields are required!", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Check for duplicate ID (only when adding or changing ID)
            if (!isEdit || !id.equals(inventoryManager.getAsset(selectedRow).getAssetId())) {
                if (inventoryManager.assetIdExists(id)) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Asset ID already exists! Please use a different ID.", 
                        "Duplicate ID", 
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            
            Asset asset;
            
            // Create appropriate asset type - demonstrates POLYMORPHISM
            if ("Electronics".equals(type)) {
                try {
                    double wattage = Double.parseDouble(specificValue);
                    if (wattage < 0) {
                        throw new NumberFormatException("Wattage cannot be negative");
                    }
                    asset = new Electronics(id, name, location, wattage);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Please enter a valid positive number for wattage!", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                asset = new Furniture(id, name, location, specificValue);
            }
            
            // Save the asset
            if (isEdit) {
                inventoryManager.updateAsset(selectedRow, asset);
                JOptionPane.showMessageDialog(this, 
                    "Asset updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                inventoryManager.addAsset(asset);
                JOptionPane.showMessageDialog(this, 
                    "Asset added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            loadTableData();
            return true;
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dialog, 
                "An error occurred: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Main method - Entry point of the application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Set look and feel to system default - with error handling
        // --- START: CORRECT LOOK AND FEEL BLOCK ---
        try {
            // This is the correct and simplest way to do it.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // If it fails, just print a message and continue. The app will still work.
            System.out.println("Could not set the system look and feel.");
            e.printStackTrace();
        }
// --- END: CORRECT LOOK AND FEEL BLOCK ---
        
        // Create and show the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
            
            // Demonstrate POLYMORPHISM in console output
            System.out.println("=== School Inventory Management System Started ===");
            InventoryManager manager = new InventoryManager();
            manager.printAllAssetDetails();
        });
    }
}