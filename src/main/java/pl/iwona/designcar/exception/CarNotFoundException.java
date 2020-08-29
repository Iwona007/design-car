package pl.iwona.designcar.exception;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(Long id) {
        super(String.format("Car with given: %d has not been found", id));
    }
}

