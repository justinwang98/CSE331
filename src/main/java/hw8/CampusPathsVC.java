package hw8;

import hw6.MarvelParser;

import java.util.List;
import java.util.Scanner;

/**
 * this is not an adt
 */
public class CampusPathsVC {
    /**
     * Creates a scanner to be utilized for sys.in
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * main method, starts the program
     * @param args: arguments to be passed in
     * @throws MarvelParser.MalformedDataException if data is malformed
     */
    public static void main(String[] args) throws MarvelParser.MalformedDataException {
        scanner = new Scanner(System.in);
        action("m"); // start
    }

    /**
     * Servers as the controller for the program, doing things based on what string is passed in
     * @param choice: choice of what command to execute
     * @throws MarvelParser.MalformedDataException if data is malformed
     */
    public static void action(String choice) throws MarvelParser.MalformedDataException {
        while (!choice.equals("q")) {
            if (choice.equals("") || choice.charAt(0) == '#') {
                System.out.println(choice);
            } else {
                if (choice.equals("m")) {
                  menu();
                } else if (choice.equals("r")) {
                  findPath();
                } else if (choice.equals("b")) {
                  printBuildings();
                } else {
                  System.out.println("Unknown option");
                }
                System.out.print("\nEnter an option ('m' to see the menu): ");
            }
            choice = scanner.nextLine();
        }
    }

    /**
     * Prints out the menu
     */
    public static void menu() {
        System.out.println(
                "Menu:\n"
                        + "\tr to find a route\n"
                        + "\tb to see a list of all buildings\n"
                        + "\tq to quit");
    }

    /**
     * Prints out the buildings
     * @throws MarvelParser.MalformedDataException if data is malformed
     */
    public static void printBuildings() throws MarvelParser.MalformedDataException {
        List<String> buildingList = CampusPathModel.buildings();
        System.out.println("Buildings:");
        for (String s : buildingList) {
            System.out.println("\t" + s);
        }
    }

    /**
     * Finds the shortest path between two given points
     * @throws MarvelParser.MalformedDataException if data is malformed
     */
    public static void findPath() throws MarvelParser.MalformedDataException {
        System.out.print("Abbreviated name of starting building: ");
        String source = scanner.nextLine();

        System.out.print("Abbreviated name of ending building: ");
        String destination = scanner.nextLine();

        List<String> path = CampusPathModel.findShortestPath(source, destination);

        if (path != null) {
            for (String s : path) {
                System.out.println(s);
            }
        }
    }
}
