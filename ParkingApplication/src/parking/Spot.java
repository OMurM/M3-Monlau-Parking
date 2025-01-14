package parking;

/**
 * @author OscarMur
 */

public class Spot {
    private final int floor;
    private final int number;
    private final String vehicleType;
    private boolean occupied;
    private Ticket assignedTicket;
    private Vehicle vehicle;

    public Spot(int floor, int number, String vehicleType) {
        this.floor = floor;
        this.number = number;
        this.vehicleType = vehicleType;
        this.occupied = false;
        this.assignedTicket = null;
        this.vehicle = null;  // No vehicle assigned initially
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void assign() {
        this.occupied = true;
    }

    public void release() {
        this.occupied = false;
        this.vehicle = null;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public Ticket getAssignedTicket() {
        return assignedTicket;
    }

    public void assignTicket(Ticket ticket) {
        this.assignedTicket = ticket;
        this.vehicle = ticket.getVehicle();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
