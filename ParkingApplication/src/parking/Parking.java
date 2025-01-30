package parking;

import parking.ParkingExceptions.ParkingFullException;
import parking.ParkingExceptions.TicketNotFoundException;
import parking.ParkingExceptions.DuplicatedLicensePlateException;

/**
 * @author OscarMur
 */

public class Parking {

    private final String id;
    private final int floors;
    private final int spotsPerFloor;
    private final Spot[][] spots;  // 2D array to represent parking spots per floor

    public Parking(String id, String name, String address, String phone, int floors, int spotsPerFloor, Spot[][] spots) {
        this.id = id;
        this.floors = floors;
        this.spotsPerFloor = spotsPerFloor;
        this.spots = new Spot[floors][spotsPerFloor];

        initializeSpots();
    }

    // Initialize parking spots
    private void initializeSpots() {
        for (int floor = 0; floor < floors; floor++) {
            for (int number = 0; number < spotsPerFloor; number++) {
                String vehicleType = determineSpotType(number);
                spots[floor][number] = new Spot(floor, number + 1, vehicleType);
            }
        }
    }

    // Determine spot type based on the spot number
    
    /*
    Dynamic spot logic always 1 spot for each vehicle free and the others random
    */
    
    private String determineSpotType(int spotNumber) {
        // Reserve 1 spot for each one
        switch (spotNumber) {
            case 0 -> {
                return "Truck";
            }
            case 1 -> {
                return "Bike";
            }
            case 2 -> {
                return "Car";
            }
            default -> {
                // Randomly assign the rest of the spots
                double rand = Math.random();
                if (rand < 0.33) {
                    return "Truck";
                } else if (rand < 0.66) {
                    return "Bike";
                } else {
                    return "Car";
                }
            }
        }
    }

    // Park a vehicle in the first available spot
    public Ticket parkVehicle(Vehicle vehicle) throws DuplicatedLicensePlateException, ParkingFullException {
        for (int floor = 0; floor < floors; floor++) {
            for (int number = 0; number < spotsPerFloor; number++) {
                Spot spot = spots[floor][number];
                if (!spot.isOccupied() && spot.getVehicleType().equalsIgnoreCase(vehicle.getType())) {
                    Ticket assignedTicket = spot.getAssignedTicket();
                    if (assignedTicket != null && assignedTicket.getVehicle().getLicensePlate().equals(vehicle.getLicensePlate())) {
                        throw new ParkingExceptions.DuplicatedLicensePlateException("Vehicle with this license plate is already parked.");
                    }

                    spot.assign();  // Asignar el espacio
                    Ticket ticket = new Ticket(String.format("%s_%d_%d", id, floor + 1, number + 1), vehicle, this); // Pass 'this' (Parking) as the third argument
                    spot.assignTicket(ticket);  // Asignar el ticket
                    return ticket;
                }
            }
        }
        throw new ParkingFullException("No available spots for the vehicle.");
    }



    // Release a vehicle and calculate parking cost
    public double releaseVehicle(String ticketId) throws TicketNotFoundException  {
        System.out.println("Attempting to release ticket: " + ticketId);
        for (int floor = 0; floor < floors; floor++) {
            for (int number = 0; number < spotsPerFloor; number++) {
                Spot spot = spots[floor][number];
                if (spot.isOccupied()) {
                    Ticket assignedTicket = spot.getAssignedTicket();
                    if (assignedTicket != null && assignedTicket.getTicketId().equals(ticketId)) {
                        spot.release();
                        double cost = calculateParkingCost(assignedTicket); // Calculate parking cost 3€*Hour
                        return cost;
                    }
                }
            }
        }
        throw new TicketNotFoundException("Ticket not found or vehicle not parked.");
    }

    // Calculate parking cost based on duration (3€/hour)
    public double calculateParkingCost(Ticket ticket) {
        long durationInMillis = ticket.getParkingTime();
        double hoursParked = durationInMillis / 3600000.0;
        double rate = 3.0;
        return hoursParked * rate;
    }

    // Display parking status with ticket parked and duration
    public void displayParkingStatus() {
        System.out.println("\n=== Parking Status ===");

        for (int floor = 0; floor < floors; floor++) {
            System.out.println("\nFloor " + (floor + 1) + ":");
            System.out.println("----------------------------");
            for (int number = 0; number < spotsPerFloor; number++) {
                Spot spot = spots[floor][number];

                if (spot.isOccupied()) {
                    Vehicle vehicle = spot.getVehicle();
                    Ticket ticket = spot.getAssignedTicket();
                    System.out.printf(
                            "Spot %d: Occupied by %s (%s) | Ticket ID: %s | Parked at: %s | Duration: %s\n",
                            number + 1,
                            vehicle.getLicensePlate(),
                            vehicle.getType(),
                            ticket.getTicketId(),
                            ticket.formattedEntryTime(),
                            ticket.getParkingDuration()
                    );
                } else {
                    System.out.printf("Spot %d: Empty\n", number + 1);
                }
            }
            System.out.println("----------------------------");
        }
    }

    public void displayAvailableParkingTickets() {
        boolean found = false;
        for (Spot[] spot1 : spots) {
            for (Spot spot : spot1) {
                if (spot != null && spot.isOccupied()) {
                    Ticket ticket = spot.getAssignedTicket();
                    Vehicle vehicle = spot.getVehicle();
                    System.out.println("Ticket ID: " + ticket.getTicketId() + " - License Plate: " + vehicle.getLicensePlate());
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No vehicles parked.");
        }
    }
    
    public Ticket getTicket(String ticketId) {
        for (Spot[] spot1 : spots) {
            for (Spot spot : spot1) {
                if (spot != null && spot.getAssignedTicket() != null && spot.getAssignedTicket().getTicketId().equals(ticketId)) {
                    return spot.getAssignedTicket();
                }
            }
        }
        return null;
    }
    
    String getId() {
        return id;
    }
}
