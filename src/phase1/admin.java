package phase1;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class admin {
    private static final String DATABASE_URL = "jdbc:sqlite:C:/Users/4rsh1y4/IdeaProjects/citytokyo2/src/database/mydb.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Card");
            System.out.println("2. Edit Card");
            System.out.println("3. Delete Card");
            System.out.println("4. View All Players");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addCard();
                    break;
                case 2:
                    editCard();
                    break;
                case 3:
                    deleteCard();
                    break;
                case 4:
                    viewAllPlayers();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void addCard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter card name:");
        String name = scanner.nextLine();
        if (DatabaseHelper.existCardName(name)) {
            System.out.println("Card with this name already exists.");
            return;
        }

        System.out.println("Enter card defense/attack (10 to 100):");
        int cardDefense = scanner.nextInt();

        System.out.println("Enter card duration (1 to 5):");
        int duration = scanner.nextInt();

        System.out.println("Enter player damage (10 to 50):");
        int playerDamage = scanner.nextInt();

        System.out.println("Enter upgrade level:");
        int upgradeLevel = scanner.nextInt();

        System.out.println("Enter upgrade cost:");
        int upgradeCost = scanner.nextInt();

        System.out.println("Enter character:");
        int character = scanner.nextInt();

        scanner.nextLine(); // consume newline

        System.out.println("Enter card type:");
        String type = scanner.nextLine();

        DatabaseHelper.insertCard(name, cardDefense, playerDamage, duration, upgradeLevel, upgradeCost, character, type);
    }

    private static void editCard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the card to edit:");
        String name = scanner.nextLine();
        if (!DatabaseHelper.existCardName(name)) {
            System.out.println("Card with this name does not exist.");
            return;
        }

        System.out.println("Enter new card defense/attack (10 to 100):");
        int cardDefense = scanner.nextInt();

        System.out.println("Enter new card duration (1 to 5):");
        int duration = scanner.nextInt();

        System.out.println("Enter new player damage (10 to 50):");
        int playerDamage = scanner.nextInt();

        System.out.println("Enter new upgrade level:");
        int upgradeLevel = scanner.nextInt();

        System.out.println("Enter new upgrade cost:");
        int upgradeCost = scanner.nextInt();

        System.out.println("Enter new character:");
        int character = scanner.nextInt();

        scanner.nextLine(); // Consume newline after reading integer input

        System.out.println("Enter new card type:");
        String type = scanner.nextLine();

        // Update the card in the database
        DatabaseHelper.updateCard(name, cardDefense, playerDamage, duration, upgradeLevel, upgradeCost, character, type);
    }

    private static void deleteCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the card to delete:");
        String name = scanner.nextLine();

        if (!DatabaseHelper.existCardName(name)) {
            System.out.println("Card with this name does not exist.");
            return;
        }

        System.out.println("Are you sure you want to delete this card? (y/n)");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            DatabaseHelper.deleteCard(name);
            System.out.println("Card deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void viewAllPlayers() {
        DatabaseHelper.loadUsersFromDatabase();
    }
}
