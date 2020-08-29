package pl.iwona.designcar.service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import pl.iwona.designcar.convert_to_enum.ConvertColor;
import pl.iwona.designcar.dto.CarDto;
import pl.iwona.designcar.exception.CarNotFoundException;
import pl.iwona.designcar.exception.ColorNotFoundException;
import pl.iwona.designcar.mapper.Mapper;
import pl.iwona.designcar.model.Car;
import pl.iwona.designcar.model.Color;
import pl.iwona.designcar.repository.CarRepository;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    private CarServiceImpl carServiceImpl;

    @Mock
    private CarRepository carRepository;

    @Mock
    Mapper mapper;

    @Mock
    CarDto carDto;

    @BeforeEach
    public void init() {
        carServiceImpl = new CarServiceImpl(carRepository, mapper);
        given(carRepository.findAll()).willReturn(prepareCarData());
    }

    @AfterEach
    public void clear() {
        carRepository.findAll().clear();
    }

    @Test
    @DisplayName("Should verify if call method map car from carDto to entity test ")
    void shouldVerifyIfCallMethodMapCarFromCarDtoToEntityTest() throws
            InvocationTargetException, IllegalAccessException {
        // given
        carServiceImpl.mappingCarDtoToEntity(carDto);
        // then
        verify(mapper).mapperFromDtoToEntity(carDto);
    }

    @Test
    @DisplayName("Should get all cars from list test")
    void shouldGetAllCarsTest() {
        // given
        List<Car> cars = carServiceImpl.getAllCars();
        // then
        assertThat(cars, hasSize(4));
    }

    @Test
    @DisplayName("Should get car by id test")
    void shouldGetByIdTest() {
        // given
        Car car = prepareBmw();
        given(carRepository.findById(car.getId())).willReturn(Optional.of(car));
        // when
        carServiceImpl.getById(car.getId());
        // then
        verify(carRepository).findById(car.getId());
    }

    @Test
    @DisplayName("Should throw car exception when car not exist test")
    void shouldThrowCarExceptionWhenCarNotExistTest() {
        // given
        Car car = prepareBmw();
        given(carRepository.findById(car.getId())).willReturn(Optional.empty());
        // when
        assertThrows(CarNotFoundException.class, () -> carServiceImpl.getById(car.getId()));
        // then
        verify(carRepository).findById(car.getId());
    }

    @Test
    @DisplayName("Should throw exception if car not exist test")
    void shouldThrowExceptionIfCarNotExistTest() {
        assertThrows(CarNotFoundException.class, () -> carServiceImpl.getById(20L));
    }

    @Test
    @DisplayName("Should add a new car test")
    void shouldAddNewCarTest() {
        // given
        Car newCar = prepareBmw();
        given(carRepository.save(newCar)).willReturn(newCar);
        // when
        Car car = carServiceImpl.save(newCar);
        // then
        verify(carRepository).save(car);
    }

    @Test
    @DisplayName("Should find car by color test")
    void shouldFindCarByColorTest() {
        // given:
        String colorName = "SILVER";
        given(carServiceImpl.findCarsByColor(Color.SILVER)).willReturn(prepareCarData().stream()
                .filter(car -> car.getColor().equals(Color.valueOf(colorName))).collect(toList()));
        // when:
        String color = carServiceImpl.findCarsByColor(Color.valueOf(colorName)).get(0).getColor().name();
        // then:
        assertEquals(colorName, color);
    }

    @Test
    @DisplayName("Should throw exception if color not exist test")
    void shouldThrowExceptionIfColorNotExistTest() {
        ConvertColor convertColor = new ConvertColor();
        assertThrows(ColorNotFoundException.class, () -> carServiceImpl
                .findCarsByColor(convertColor.convertToEnum("ORANGE")));
    }

    @Test
    @DisplayName("Should find by mark test")
    void shouldFindByMarkTest() {
        // given:
        String markName = "Opel";
        List<Car> opelCars = new ArrayList<>();
        opelCars.add(prepareOpel());
        given(carRepository.findAllByMark(markName)).willReturn(opelCars);
        // when:
        List<Car> foundCars = carServiceImpl.findByMark(markName);
        // then:
        assertEquals(opelCars, foundCars);
    }

    @Test
    @DisplayName("Should modify attributes in car test")
    void ShouldModifyAttributesInCarTest() {
        // given:
        Car bmw = prepareBmw();
        // when:
        carServiceImpl.editCar(bmw.getMark(), bmw.getModel(), bmw.getImageName(), bmw.getColor(),
                bmw.getDateOfProduction(), bmw.getId());
        // then:
        verify(carRepository).editCar(bmw.getMark(), bmw.getModel(), bmw.getImageName(), bmw.getColor().name(),
                bmw.getDateOfProduction(), bmw.getId());
    }

    @Test
    @DisplayName("Should change car's color with given id test")
    void ShouldChangeCarColorWithGivenIdTest() {
        // given:
        Color newColor = Color.RED;
        Long carId = prepareMercedes().getId();
        // when:
        carServiceImpl.changeColor(newColor, carId);
        // then:
        verify(carRepository).changeColor(newColor.name(), carId);
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
