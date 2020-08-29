package pl.iwona.designcar.convert_to_enum;

import java.util.EnumSet;
import org.springframework.stereotype.Component;
import pl.iwona.designcar.exception.ColorNotFoundException;
import pl.iwona.designcar.model.Color;

@Component
public class ConvertColor {
    public Color convertToEnum(String color) {
        return EnumSet.allOf(Color.class).stream()
                .filter(color1 -> color1.name().equalsIgnoreCase(color))
                .findAny()
                .orElseThrow(() -> new ColorNotFoundException(color));
    }
}
