import java.io.*;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;



public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;
    public ArrayList<BuddyInfo> BuddyNames = new ArrayList<BuddyInfo>();

    public AddressBook() {
        BuddyNames = new ArrayList<>();
    }

    public void addBuddy(BuddyInfo BuddyName) {
        BuddyNames.add(BuddyName);
    }

    public void removeBuddy(BuddyInfo BuddyName) {
        BuddyNames.remove(BuddyName);

        // Testing tests to test all tests
        // Testing tests in this new branch
    }

    public void save(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (BuddyInfo buddy : BuddyNames) {
                writer.write(buddy.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving.");
        }
    }

    // Serialized Save
    public void saveSerialized(String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        }
    }

    // DeSerialized Load
    public static AddressBook loadSerialized(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (AddressBook) ois.readObject();
        }
    }



    public static AddressBook importAddressBook(String fileName) {
        AddressBook addressBook = new AddressBook();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Delegate to importBuddyInfo to create a BuddyInfo object
                BuddyInfo buddy = BuddyInfo.importBuddyInfo(line);
                addressBook.addBuddy(buddy);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error in file format: " + e.getMessage());
        }

        return addressBook;
    }

    public ArrayList<BuddyInfo> getBuddies() {
        return new ArrayList<>(BuddyNames);
    }


    public String toXML(){
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<AddressBook>\n");

        for (BuddyInfo buddy : this.BuddyNames) {
            xmlBuilder.append(" <BuddyInfo>\n");
            xmlBuilder.append("  <Name>").append(buddy.getName()).append("</Name>\n");
            xmlBuilder.append("  <PhoneNumber").append(buddy.getPhoneNumber()).append(("</PhoneNumber>\n"));
            xmlBuilder.append(" </BuddyInfo>\n");

        }

        xmlBuilder.append("</AddressBook>");

        return xmlBuilder.toString();
    }

    public void exportToXmlFile(String filePath){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Convert the AddressBook to XML and write it to the file
            writer.write(this.toXML());
            System.out.println("AddressBook exported successfully as " + filePath);
        } catch (IOException e) {
            System.err.println("Error exporting AddressBook to XML: " + e.getMessage());
        }
    }

    public void importFromXmlFile(String filePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Handler for SAX events
            DefaultHandler handler = new DefaultHandler() {
                private StringBuilder currentValue = new StringBuilder();
                private BuddyInfo currentBuddy;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    currentValue.setLength(0); // Clear the buffer
                    if (qName.equalsIgnoreCase("BuddyInfo")) {
                        currentBuddy = new BuddyInfo(); // Start a new BuddyInfo object
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    currentValue.append(ch, start, length); // Collect text between tags
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (currentBuddy != null) {
                        switch (qName) {
                            case "Name":
                                currentBuddy.setName(currentValue.toString());
                                break;
                            case "PhoneNumber":
                                currentBuddy.setPhoneNumber(currentValue.toString());
                                break;
                            case "BuddyInfo":
                                addBuddy(currentBuddy); // Add the buddy to the AddressBook
                                currentBuddy = null;
                                break;
                        }
                    }
                }
            };

            // Parse the XML file
            saxParser.parse(new File(filePath), handler);
            System.out.println("AddressBook imported successfully from " + filePath);
        } catch (Exception e) {
            System.err.println("Error importing AddressBook from XML: " + e.getMessage());
        }
    }

}
