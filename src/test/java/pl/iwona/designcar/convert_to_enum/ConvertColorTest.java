package pl.iwona.designcar.convert_to_enum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.iwona.designcar.exception.ColorNotFoundException;
import pl.iwona.designcar.model.Color;

import static org.junit.jupiter.api.Assertions.*;

class ConvertColorTest {
    private ConvertColor convertColor;

    @BeforeEach
    void initConverColor() {
        convertColor = new ConvertColor();
    }

    @Test
    @DisplayName("Should given string color convert to enumTest")
    void shouldGivenStringColorConvertToEnumTest() {
        // given:
        String color = "BLUE";
        // when:
        Color colorEnum = convertColor.convertToEnum(color);
        // then:
        assertNotEquals(color, colorEnum);
    }

    @Test
    @DisplayName("Should return the same name of color when convert String to enum test")
    void shouldReturnTheSameNameOfColorTest() {
        // given:
        String color = "BLUE";
        // when:
        Color colorEnum = convertColor.convertToEnum(color);
        // then:
        assertEquals(color, colorEnum.getName());
    }


    @Test
    @DisplayName("Should throw color not exist exception test")
    void shouldThrowColorNotExistExceptionTest() {
        // given:
        String color = "Orange";
        // then:
        assertThrows(ColorNotFoundException.class, () -> convertColor.convertToEnum(color));
    }

    @Test
    @DisplayName("Should return the same name of color when convert String to enum test")
    void shouldConvertToEnumStringColorAndIgnoreCaseTest() {
        // given:
        String color = "Blue";
        // when:
        Color colorEnum = convertColor.convertToEnum(color);
        // then:
        assertEquals(colorEnum, Color.BLUE);
    }
}
