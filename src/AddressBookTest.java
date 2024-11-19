import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressBookTest {

    @Test
    public void testExportAndImportAddressBook() {
        // Step 1: Create an AddressBook and add some BuddyInfo objects
        AddressBook originalAddressBook = new AddressBook();
        originalAddressBook.addBuddy(new BuddyInfo("Alice", "123 Wonderland", "123-456-7890"));
        originalAddressBook.addBuddy(new BuddyInfo("Bob", "456 Nowhere Street", "987-654-3210"));

        // Step 2: Export the AddressBook to a file
        String fileName = "testAddressBook.txt";
        originalAddressBook.save(fileName);

        // Ensure the file was created
        File file = new File(fileName);
        assertTrue(file.exists());

        // Step 3: Import the AddressBook from the file
        AddressBook importedAddressBook = AddressBook.importAddressBook(fileName);

        // Step 4: Assert that both AddressBooks contain the same data
        ArrayList<BuddyInfo> originalBuddies = originalAddressBook.getBuddies();
        ArrayList<BuddyInfo> importedBuddies = importedAddressBook.getBuddies();

        assertEquals(originalBuddies.size(), importedBuddies.size(), "Both AddressBooks should have the same number of buddies");

        for (int i = 0; i < originalBuddies.size(); i++) {
            assertEquals(originalBuddies.get(i).toString(), importedBuddies.get(i).toString(), "BuddyInfo objects should match");
        }

        // Clean up the test file
        file.delete();
    }
}
