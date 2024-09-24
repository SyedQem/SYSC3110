import java.util.LinkedList;

public class AddressBook {

    private LinkedList<BuddyInfo> BuddyNames = new LinkedList<BuddyInfo>();

    public AddressBook() {
        BuddyNames = new LinkedList<>();
    }

    public void addBuddy(BuddyInfo BuddyName){
        BuddyNames.add(BuddyName);
    }

    public void removeBuddy(BuddyInfo BuddyName){
        BuddyNames.remove(BuddyName);

    // Testing tests to test all tests
        // Testing tests in this new branch
    }
}
