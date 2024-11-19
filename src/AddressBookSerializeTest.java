import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressBookSerializeTest {

    @Test
    public void testSerialization() throws Exception {
        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(new BuddyInfo("Alice", "123 Wonderland", "123-456-7890"));
        addressBook.addBuddy(new BuddyInfo("Bob", "456 Nowhere", "987-654-3210"));

        String fileName = "testSerializedAddressBook.ser";
        addressBook.saveSerialized(fileName);

        File file = new File(fileName);
        assertTrue(file.exists(), "Serialized file should exist");

        AddressBook deserializedAddressBook = AddressBook.loadSerialized(fileName);

        assertEquals(addressBook.getBuddies().size(), deserializedAddressBook.getBuddies().size(),
                "Both AddressBooks should have the same number of buddies");
        assertEquals(addressBook.getBuddies().toString(), deserializedAddressBook.getBuddies().toString(),
                "Both AddressBooks should have identical data");

        file.delete(); // Clean up
    }
}
