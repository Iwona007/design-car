package pl.iwona.designcar.mapper;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.iwona.designcar.dto.CarDto;
import pl.iwona.designcar.model.Car;
import pl.iwona.designcar.model.Color;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    private Mapper mapper;

    @BeforeEach
    void initMapper() {
        mapper = new Mapper();
    }

    @Test
    @DisplayName("Should map car from carDto to entity test ")
    void shouldMapCarFromCarDtoToEntityTest() throws InvocationTargetException, IllegalAccessException {
        // given:
        CarDto carDto = new CarDto();
        carDto.setMark("Tesla");
        carDto.setModel("M3");
        carDto.setColor(Color.SILVER);
        carDto.setDateOfProduction(LocalDate.now());
        // when:
        Car car = (Car) mapper.mapperFromDtoToEntity(carDto);
        // then:
        assertEquals(carDto.getMark(), car.getMark());
        assertEquals(carDto.getModel(), car.getModel());
        assertEquals(carDto.getColor(), car.getColor());
        assertEquals(carDto.getDateOfProduction(), car.getDateOfProduction());
    }

    @Test
    @DisplayName("Should map car from entity to carDTo test ")
    void shouldMapCarFromEntityToCarDtoTest() throws InvocationTargetException, IllegalAccessException {
        // given:
        Car car = new Car();
        car.setMark("Opel");
        car.setModel("Astra");
        car.setColor(Color.BLUE);
        car.setDateOfProduction(LocalDate.now());
        // when:
        CarDto carDto = (CarDto) mapper.mapperFromEntityToDto(car);
        // then:
        assertEquals(car.getMark(), carDto.getMark());
        assertEquals(car.getModel(), carDto.getModel());
        assertEquals(car.getColor(), carDto.getColor());
        assertEquals(car.getDateOfProduction(), carDto.getDateOfProduction());
    }

    @Test
    @DisplayName("should throw IllegalAccessException when mapping from entity to CarDto")
    void shouldThrowIllegalAccessExceptionWhenMappingFromEntityToCarDto() {
        // given:
        CarDto carDto = new CarDto();
        // then:
        assertThrows(IllegalAccessException.class, () -> mapper.mapperFromEntityToDto(carDto));
    }
}
