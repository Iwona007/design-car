package pl.iwona.designcar.exception;

public class ColorNotFoundException extends RuntimeException {

    public ColorNotFoundException(String color) {
        super(String.format("Invalid enum type of color %s", color));
    }
}
