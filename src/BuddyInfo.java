import java.io.Serializable;
import java.util.Scanner;

public class BuddyInfo implements Serializable {
    private static final long serialVersionUID = 1L;


    private String name;
    private String address;
    private String phoneNumber;

    public BuddyInfo() {
        name = null;
        address = null;
        phoneNumber = null;
    }

    public BuddyInfo(String name, String address, String phoneNumber) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name + "#" + address + "#" + phoneNumber;
    }


    public static BuddyInfo importBuddyInfo(String data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Input string can't be empty");
        }

        Scanner scanner = new Scanner(data);
        scanner.useDelimiter("#");

        try {
            String name = scanner.next();
            String address = scanner.next();
            String phoneNumber = scanner.next();

            return new BuddyInfo(name, address, phoneNumber);
        } catch (Exception e) {
            throw new IllegalArgumentException("Input string is not formatted correctly.", e);
        } finally {
            scanner.close();
        }
    }

    public boolean equals(String name) {
        return this.name.equals(name);
    }


    public static void main(String[] args) {
        BuddyInfo friend = new BuddyInfo("Homer", "1131 Secord Ave", "21");
        System.out.println("Hello " + friend.getName());

    }

}

