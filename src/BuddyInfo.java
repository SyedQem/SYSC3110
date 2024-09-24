public class BuddyInfo {


    private String name;
    private String address;
    private int phoneNumber;

    public BuddyInfo(){
        name = null;
        address = null;
        phoneNumber = 0;
    }

    public BuddyInfo(String name, String address, int phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public static void main(String[] args) {
        BuddyInfo friend = new BuddyInfo("Homer", "1131 Secord Ave", 21);
        System.out.println("Hello " + friend.getName());

    }
}
