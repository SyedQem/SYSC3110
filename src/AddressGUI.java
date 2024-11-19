import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class AddressGUI extends JFrame {

    private JMenuBar menuBar;
    private JMenu addressBookMenu, buddyInfoMenu;
    private JMenuItem newAddressBookItem, addBuddyItem, removeBuddyItem, displayBuddyItem, exportAddressBookItem, importAddressBookItem;
    private JList<BuddyInfo> buddyList;
    private JMenuItem serializeExportItem, serializeImportItem;
    private DefaultListModel<BuddyInfo> buddyListModel;
    private AddressBook addressBook;

    private void newAddressBook(){
        addressBook = new AddressBook();
        buddyListModel.clear();
        JOptionPane.showMessageDialog(this, "New Address Book Created");
    }

    private void addBuddy(){
        String name = JOptionPane.showInputDialog(this, "Enter Buddy Name:");
        String address = JOptionPane.showInputDialog(this, "Enter Buddy Address:");
        String phoneNumber = JOptionPane.showInputDialog(this, "Enter Buddy Phone Number:");

        if (name != null && !name.trim().isEmpty()) {
            BuddyInfo newBuddy = new BuddyInfo(name, address, phoneNumber);  // Assuming BuddyInfo has a constructor with name
            addressBook.addBuddy(newBuddy);
            buddyListModel.addElement(newBuddy);  // Add to the JList model
        }

    }

    private void removeBuddy(){
        String name = JOptionPane.showInputDialog(this, "Enter Buddy Name to Remove:");

        // Validate that the input is not null or empty
        if (name != null && !name.trim().isEmpty()) {
            // Search for the BuddyInfo object with the given name in the AddressBook
            for (BuddyInfo buddy : addressBook.BuddyNames) {
                if (buddy.getName().equals(name)) {
                    // Remove the buddy from the AddressBook
                    addressBook.removeBuddy(buddy);

                    // Remove the buddy from the JList model
                    buddyListModel.removeElement(buddy.toString());
                    break;
                }
            }
        }
    }

    private void exportAddressBook() {
        String fileName = JOptionPane.showInputDialog(this, "Enter file name to save under");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try {
                addressBook.save(fileName);
                JOptionPane.showMessageDialog(this, "Address Book saved under " + fileName);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving Address Book: " + e.getMessage());
            }
        }
    }

    private void importAddressBook() {
        String fileName = JOptionPane.showInputDialog(this, "Enter file name to import the Address Book:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try {
                AddressBook importedAddressBook = AddressBook.importAddressBook(fileName);
                addressBook = importedAddressBook;

                // Update the buddy list in the GUI
                buddyListModel.clear();
                for (BuddyInfo buddy : addressBook.getBuddies()) {
                    buddyListModel.addElement(buddy);
                }

                JOptionPane.showMessageDialog(this, "Address Book imported from " + fileName);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error importing Address Book: " + e.getMessage());
            }
        }
    }

    private void exportSerialized(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog(this, "Enter file name to save:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try {
                addressBook.saveSerialized(fileName);
                JOptionPane.showMessageDialog(this, "Address Book serialized to " + fileName);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving Address Book: " + ex.getMessage());
            }
        }
    }

    private void importSerialized(ActionEvent e) {
        String fileName = JOptionPane.showInputDialog(this, "Enter file name to load:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try {
                addressBook = AddressBook.loadSerialized(fileName);
                JOptionPane.showMessageDialog(this, "Address Book deserialized from " + fileName);
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error loading Address Book: " + ex.getMessage());
            }
        }
    }




    public AddressGUI() {
        setTitle("Address Book");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Creating AddressBook object
        addressBook = new AddressBook();

        // Initializing our buddy list model and JList
        buddyListModel = new DefaultListModel<>();
        buddyList = new JList<>(buddyListModel);

        // Setting up our Menu Bar
        menuBar = new JMenuBar();

        // Set up the menus
        addressBookMenu = new JMenu("Address Book");
        buddyInfoMenu = new JMenu("Buddy Info");

        // Set up the menu items
        newAddressBookItem = new JMenuItem("New Address Book");
        addBuddyItem = new JMenuItem("Add Buddy");
        removeBuddyItem = new JMenuItem("Remove Buddy");
        displayBuddyItem = new JMenuItem("Display buddies");
        exportAddressBookItem = new JMenuItem("Export Address Book");
        importAddressBookItem = new JMenuItem("Import Address Book");
        serializeExportItem = new JMenuItem("Export (Serialized");
        serializeImportItem = new JMenuItem("Import (Deserialized");



        // Add ActionListeners to menu items
        newAddressBookItem.addActionListener(e -> newAddressBook());
        addBuddyItem.addActionListener(e -> addBuddy());
        removeBuddyItem.addActionListener(e -> removeBuddy());
        exportAddressBookItem.addActionListener(e -> exportAddressBook());
        importAddressBookItem.addActionListener(e -> importAddressBook());
        serializeImportItem.addActionListener(this::importSerialized);
        serializeExportItem.addActionListener(this::importSerialized);

        // Adding items to menu
        addressBookMenu.add(newAddressBookItem);
        addressBookMenu.add(exportAddressBookItem);
        addressBookMenu.add(importAddressBookItem);
        addressBookMenu.add(serializeExportItem);
        addressBookMenu.add(serializeImportItem);



        buddyInfoMenu.add(addBuddyItem);
        buddyInfoMenu.add(removeBuddyItem);
        buddyInfoMenu.add(displayBuddyItem);

        // Add Menus to the menu bar

        menuBar.add(addressBookMenu);
        menuBar.add(buddyInfoMenu);

        // Set the menu bar
        setJMenuBar(menuBar);

        // Add the buddyList to the frame
        add(new JScrollPane(buddyList));

        // Make the frame visible
        setVisible(true);

    }

    public static void main(String[] args) {
        // Run the GUI application
        SwingUtilities.invokeLater((AddressGUI::new));
    }
}
