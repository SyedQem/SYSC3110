import java.util.LinkedList;

public class AddressBook {


    private LinkedList<BuddyInfo> BuddyNames = new LinkedList<BuddyInfo>();



    private void addBuddy(BuddyInfo BuddyName){
        BuddyNames.add(BuddyName);
    }

    private void removeBuddy(BuddyInfo BuddyName){
        BuddyNames.remove(BuddyName);
    }
}
