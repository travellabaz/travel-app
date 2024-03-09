package az.travellab.ms_travel_application.exception;


public class CustomFeignException extends RuntimeException {
    private final int status;
    public CustomFeignException(String message, int status) {
        super(message);
        this.status = status;
    }
}