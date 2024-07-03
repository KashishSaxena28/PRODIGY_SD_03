package ContactManagementSystem;

import java.io.*;
import java.util.*;

public class ContactManager {
    private static List<Contact> contacts = new ArrayList<>();
    private static final String CONTACTS_FILE = "contacts.txt";

    public static void main(String[] args) {
        loadContacts();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Add new contact");
            System.out.println("2. View contacts");
            System.out.println("3. Edit contact");
            System.out.println("4. Delete contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewContact(scanner);
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    editContact(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    break;
                case 5:
                    saveContacts();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void addNewContact(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email address: ");
        String email = scanner.nextLine();
        
        contacts.add(new Contact(name, phoneNumber, email));
        System.out.println("Contact added successfully.");
    }

    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }
        
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            System.out.println((i + 1) + ". " + contact);
        }
    }

    private static void editContact(Scanner scanner) {
        viewContacts();
        System.out.print("Enter the contact number to edit: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline
        
        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number.");
            return;
        }
        
        Contact contact = contacts.get(index);
        System.out.print("Enter new name (" + contact.getName() + "): ");
        String name = scanner.nextLine();
        System.out.print("Enter new phone number (" + contact.getPhoneNumber() + "): ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter new email address (" + contact.getEmail() + "): ");
        String email = scanner.nextLine();
        
        if (!name.isEmpty()) contact.setName(name);
        if (!phoneNumber.isEmpty()) contact.setPhoneNumber(phoneNumber);
        if (!email.isEmpty()) contact.setEmail(email);
        
        System.out.println("Contact updated successfully.");
    }

    private static void deleteContact(Scanner scanner) {
        viewContacts();
        System.out.print("Enter the contact number to delete: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline
        
        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number.");
            return;
        }
        
        contacts.remove(index);
        System.out.println("Contact deleted successfully.");
    }

    private static void loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONTACTS_FILE))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing contacts found. Starting fresh.");
        }
    }

    private static void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONTACTS_FILE))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }
}


