package parking;

import java.util.Scanner;

/**
 * @author OscarMur
 */

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Create the parking system
            ParkingSystem parkingSystem = new ParkingSystem();

            // Create a parking lot
            Spot[][] spots = new Spot[2][3];                                                             // Floors // Spots
            Parking parking = new Parking("PR123", "Monlau Parking", "C/ Monlau 6, Barcelona", "+34 666 66 66", 2, 3, spots);

            // Add parking lot to the system
            parkingSystem.addParkingLot(parking);

            boolean running = true;

            while (running) {
                System.out.println("\n=== Parking Menu ===");
                System.out.println("1. Register a vehicle");
                System.out.println("2. Check parking status");
                System.out.println("3. Release a vehicle");
                System.out.println("4. Exit");
                System.out.print("Select an option: ");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> {
                        // Register a vehicle
                        System.out.print("Enter the license plate: ");
                        String plate = scanner.nextLine();
                        System.out.print("Enter the color: ");
                        String color = scanner.nextLine();

                        // Prompt for vehicle type
                        System.out.println("Select vehicle type:");
                        System.out.println("1. Car");
                        System.out.println("2. Bike");
                        System.out.println("3. Truck");
                        System.out.print("Enter the number of the vehicle type: ");
                        int vehicleTypeChoice = scanner.nextInt();
                        scanner.nextLine(); // Clear buffer
                        String type = "";

                        switch (vehicleTypeChoice) {
                            case 1 -> type = "Car";
                            case 2 -> type = "Bike";
                            case 3 -> type = "Truck";
                            default -> {
                                System.out.println("Invalid vehicle type. Defaulting to Car.");
                                type = "Car";
                            }
                        }

                        // Create the vehicle
                        Vehicle vehicle = new Vehicle(plate, color, type);
                        // Try to park the vehicle in the first available parking lot
                        Ticket ticket = parkingSystem.getParkingLot(parking.getId()).parkVehicle(vehicle);

                        if (ticket != null) {
                            System.out.println("Vehicle registered successfully. Ticket ID: " + ticket.getTicketId());
                        } else {
                            System.out.println("No available spots for " + vehicle.getType());
                        }
                    }


                    case 2 -> // Check parking status
                        parkingSystem.displayAllParkingStatus();

                    case 3 -> {
                        // Release a vehicle
                        System.out.print("Enter the ticket ID: ");
                        System.out.println("Avalivable tickets");
                        String ticketId = scanner.nextLine();

                        double cost = parkingSystem.getParkingLot(parking.getId()).releaseVehicle(ticketId);
                        if (cost >= 0) {
                            System.out.println("Vehicle released. Parking cost: " + cost + " EUR");
                        } else {
                            System.out.println("Ticket not found or vehicle not parked.");
                        }
                    }

                    case 4 -> {
                        // Exit
                        running = false;
                        System.out.println("Exiting the parking system. Goodbye!");
                    }

                    default -> System.out.println("Invalid option. Try again.");
                }
            }
        }
    }
}
