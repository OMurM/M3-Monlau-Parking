package parking;

/**
 * @author oscarmurmat
 */

public class ParkingExceptions {
    
    public static class ParkingFullException extends Exception {
        public ParkingFullException(String message) {
            super(message);
        }
    }

    public static class TicketNotFoundException extends Exception {
        public TicketNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class InvalidLicensePlateException extends Exception {
        public InvalidLicensePlateException(String message) {
            super (message);
        }
    }

    public static class DuplicatedLicensePlateException extends Exception {
        public DuplicatedLicensePlateException(String message) {
            super(message);
        }
    }
}
