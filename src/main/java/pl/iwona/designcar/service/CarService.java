package pl.iwona.designcar.service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import pl.iwona.designcar.dto.CarDto;
import pl.iwona.designcar.model.Car;
import pl.iwona.designcar.model.Color;

public interface CarService {

    Car mappingCarDtoToEntity(CarDto dto) throws InvocationTargetException, IllegalAccessException;

    List<Car> getAllCars();

    Car save(Car car);

    Optional<Car> getById(Long id);

    List<Car> findCarsByColor(Color color);

    List<Car> findByMark(String mark);

    void editCar(String mark, String model, String imageName,
                 Color color, LocalDate dateOfProduction, Long id);

    void changeColor(Color color, Long id);

    void removeById(Long id);

}
