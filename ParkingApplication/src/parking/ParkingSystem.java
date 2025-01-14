package parking;

import java.util.HashMap;
import java.util.Map;

/**
 * @author OscarMur
 */

public class ParkingSystem {

    // Store parking lots by their unique ID
    private final Map<String, Parking> parkingLots;

    public ParkingSystem() {
        parkingLots = new HashMap<>();
    }

    // Add a parking lot to the system
    public void addParkingLot(Parking parking) {
        parkingLots.put(parking.getId(), parking);
    }

    // Get a parking lot by ID
    public Parking getParkingLot(String id) {
        return parkingLots.get(id);
    }

    // Display status of all parking lots
    public void displayAllParkingStatus() {
        for (Parking parking : parkingLots.values()) {
            parking.displayParkingStatus();
        }
    }
}
