package parking;

/**
 * @author OscarMur
 */

public class Vehicle {

    private String licensePlate;
    private String color;
    private String type; //Truck, Bike, Car
    
    //Constructor
    public Vehicle(String licensePlate, String color, String type) {
        this.licensePlate = licensePlate;
        this.color = color;
        this.type = type;
    }
    
    //Getters
    public String getLicensePlate() {
        return licensePlate;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }
    
    //Setters
    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
