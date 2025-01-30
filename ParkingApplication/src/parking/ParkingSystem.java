package parking;

import parking.ParkingExceptions.DuplicatedLicensePlateException;
import parking.ParkingExceptions.TicketNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import parking.ParkingExceptions.ParkingFullException;

/**
 * @author OscarMur
 */

public class ParkingSystem {

    private final Set<String> registeredLicensePlates; // Store license for duplicated errors 
    // Store parking lots by their unique ID
    private final Map<String, Parking> parkingLots;

    public ParkingSystem() {
        registeredLicensePlates = new HashSet<>();
        parkingLots = new HashMap<>();
    }

    // Add a parking lot to the system
    public void addParkingLot(Parking parking) {
        parkingLots.put(parking.getId(), parking);
    }
    
    public Ticket parkVehicle(Parking parking, Vehicle vehicle) throws ParkingFullException, DuplicatedLicensePlateException {
        if (registeredLicensePlates.contains(vehicle.getLicensePlate())) {
            throw new DuplicatedLicensePlateException("Vehicle with license plate: " + vehicle.getLicensePlate() + " is already parked.");
        }

        Ticket ticket = parking.parkVehicle(vehicle);
        registeredLicensePlates.add(vehicle.getLicensePlate());
        return ticket;
    }

    
    public double releaseVehicle(String ticketId) throws TicketNotFoundException {
        Ticket ticket = getTicket(ticketId);
        if (ticket != null) {
            Parking parkingLot = ticket.getParking();  
            if (parkingLot != null) {
                double cost = parkingLot.calculateParkingCost(ticket);  
                registeredLicensePlates.remove(ticket.getVehicle().getLicensePlate());  // Eliminar la matrícula del registro
                return cost;
            } else {
                throw new TicketNotFoundException("Parking lot not found for this ticket.");
            }
        }
        throw new TicketNotFoundException("Ticket not found.");
    }
    
    public Ticket getTicket(String ticketId) {
        return null;
    }
    
    
    // Get a parking lot by ID
    public Parking getParkingLot(String id) {
        Parking parkingLot = parkingLots.get(id);
        
        if (parkingLot == null) {
            throw new IllegalArgumentException("Parking lot with ID: " + id + " not found");
        }
        
        return parkingLot;
    }

    // Display status of all parking lots
    public void displayAllParkingStatus() {
        for (Parking parking : parkingLots.values()) {
            parking.displayParkingStatus();
        }
    }
}
