package parking;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

/**
 * @author OscarMur
 */

public class Ticket {
    private final String ticketId;
    private final LocalDateTime entryTime;
    private final Vehicle vehicle;
    private final Parking parking;
    
    // Constructor
    public Ticket(String ticketId, Vehicle vehicle, Parking parking) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parking = parking;
        this.entryTime = LocalDateTime.now();
    }

    // Getters
    public String getTicketId() {
        return ticketId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Parking getParking() {
        return parking;
    }

    // Format entry time to "dd/MM/yyyy HH:mm"
    public String formattedEntryTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return entryTime.format(formatter);
    }

    // Calculate parking duration in minutes
    public long getParkingDurationMinutes() {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(entryTime, now);
        return duration.toMinutes();
    }

    // Calculate the output ticket on hours and minutes
    public String getParkingDuration() {
        long minutes = getParkingDurationMinutes();
        long hours = minutes / 60;
        minutes = minutes % 60;
        return String.format("%d hours, %d minutes", hours, minutes);
    }

    // Get the parking time in milliseconds
    public long getParkingTime() {
        Duration duration = Duration.between(entryTime, LocalDateTime.now());
        return duration.toMillis();
    }
}
