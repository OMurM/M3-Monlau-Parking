package parking;

/**
 * @author OscarMur
 */

public class Parking {

    private final String id;
    private final String name;
    private final String address;
    private final String phone;
    private final int floors;
    private final int spotsPerFloor;
    private final Spot[][] spots;  // 2D array to represent parking spots per floor

    public Parking(String id, String name, String address, String phone, int floors, int spotsPerFloor, Spot[][] spots) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
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
    public Ticket parkVehicle(Vehicle vehicle) {
        for (int floor = 0; floor < floors; floor++) {
            for (int number = 0; number < spotsPerFloor; number++) {
                Spot spot = spots[floor][number];
                if (!spot.isOccupied() && spot.getVehicleType().equalsIgnoreCase(vehicle.getType())) {
                    spot.assign();
                    Ticket ticket = new Ticket(String.format("%s_%d_%d", id, floor + 1, number + 1), vehicle);
                    spot.assignTicket(ticket); // Assign the ticket to the spot
                    return ticket;
                }
            }
        }
        System.out.println("No available spots for " + vehicle.getType());
        return null;
    }

    // Release a vehicle and calculate parking cost
    public double releaseVehicle(String ticketId) {
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
        System.out.println("Ticket not found or the vehicle is not parked.");
        return 0.0;
    }

    // Calculate parking cost based on duration (3€/hour)
    private double calculateParkingCost(Ticket ticket) {
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

    String getId() {
        return id;
    }
}
