package pl.iwona.designcar.controller;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import pl.iwona.designcar.convert_to_enum.ConvertColor;
import pl.iwona.designcar.dto.CarDto;
import pl.iwona.designcar.model.Car;
import pl.iwona.designcar.model.Color;
import pl.iwona.designcar.service.CarService;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarControllersTest {

    @Mock
    Car car;

    @Mock
    CarDto carDto;

    @Mock
    CarService carService;

    private CarControllers carControllers;

    private ConvertColor convertColor;

    @BeforeEach
    public void init() {
        carControllers = new CarControllers(carService, convertColor);
        given(carService.getAllCars()).willReturn(prepareCarData());
    }

    @AfterEach
    public void clear() {
        carService.getAllCars().clear();
    }

    @Test
    @DisplayName("Should return status ok when get all cars Test")
    void shouldReturnStatusOkWhenGetAllCarsTest() {
        // given:
        // when:
        when(carService.getAllCars()).thenReturn(prepareCarData());
        // then:
        assertEquals(HttpStatus.OK, carControllers.getAllCars().getStatusCode());
    }

    @Test
    @DisplayName("Should return status ok when save car test")
    void shouldReturnStatusOkWhenSaveCarTest() throws InvocationTargetException, IllegalAccessException, URISyntaxException {
        // given:
        // when:
        when(carService.mappingCarDtoToEntity(carDto)).thenReturn(car);
        when(carService.save(car)).thenReturn(car);
        // then:
        assertEquals(HttpStatus.OK, carControllers.save(carDto).getStatusCode());
    }

    @Test
    @DisplayName("Should return not found status when try to get car by id which not exist test")
    void shouldReturnNotFoundStatusWhenTryToGetCarByIdWhichNotExistTest() {
        // given:
        Car car = prepareBmw();
        given(carService.getById(car.getId())).willReturn(Optional.empty());
        // then:
        assertEquals(HttpStatus.NOT_FOUND, carControllers.getById(car.getId()).getStatusCode());
    }

    @Test
    @DisplayName("Should return body which is equals to car when response status is ok and is using get by id method test")
    void shouldReturnBodyWhichIsEqualsToCarWhenResponseStatusIsOkAndIsUsingGetByIdMethodTest() {
        // given:
        Car car = prepareBmw();
        given(carService.getById(car.getId())).willReturn(Optional.of(car));
        // then:
        assertEquals(car, carControllers.getById(car.getId()).getBody());
    }

    @Test
    void findByMark() {
        // given:
        List<Car> cars = prepareCarData();
        String mark = "BMW";
        // when:
        when(carService.findByMark(mark)).thenReturn(cars);
        // then:
        assertEquals(HttpStatus.OK, carControllers.findByMark(mark).getStatusCode());
    }

    @Test
    @DisplayName("should return Ok Status When User What To Remove Car By Id Test")
    void shouldReturnOkStatusWhenUserWhatToRemoveCarByIdTest() {
        // given:
        Car car = prepareBmw();
        given(carService.getById(car.getId())).willReturn(Optional.of(car));
        // then:
        assertEquals(HttpStatus.OK, carControllers.removeById(car.getId()).getStatusCode());
    }

    @Test
    @DisplayName("should Return Not Found Status When User What To Remove Car By Id Which Not Exist Test")
    void shouldReturnNotFoundStatusWhenUserWhatToRemoveCarByIdWhichNotExistTest() {
        // given:
        Car car = prepareBmw();
        given(carService.getById(car.getId())).willReturn(Optional.empty());
        // then:
        assertEquals(HttpStatus.NOT_FOUND, carControllers.removeById(car.getId()).getStatusCode());
    }

    private List<Car> prepareCarData() {
        return Stream.of(prepareOpel(), prepareBmw(), prepareMercedes(), prepareTesla()).collect(toList());
    }

    private Car prepareBmw() {
        return new Car(1L, "BMW", "599 GTB Fiorano", Color.BLUE,
                LocalDate.of(2010, 1, 2), "Germany");
    }

    private Car prepareMercedes() {
        return new Car(2L, "Mercedes", "E200", Color.SILVER,
                LocalDate.of(2000, 1, 3), "Poland");
    }

    private Car prepareOpel() {
        return new Car(3L, "Opel", "599 GTB", Color.BLUE,
                LocalDate.of(2010, 1, 2), "Germany");
    }

    private Car prepareTesla() {
        return new Car(4L, "Tesla", "Model3", Color.SILVER,
                LocalDate.of(2011, 2, 1), "England");
    }

}
