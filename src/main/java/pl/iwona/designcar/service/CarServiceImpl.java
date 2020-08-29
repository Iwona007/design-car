package pl.iwona.designcar.service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.iwona.designcar.dto.CarDto;
import pl.iwona.designcar.mapper.Utils;
import pl.iwona.designcar.model.Car;
import pl.iwona.designcar.model.Color;
import pl.iwona.designcar.repository.CarRepository;


@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private Utils utils;
    private List<Car> cars;

    public CarServiceImpl(CarRepository carRepository, Utils utils) {
        this.carRepository = carRepository;
        this.utils = utils;
        this.cars = new ArrayList<>();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Car car1 = new Car("Ferrari", "599 GTB Fiorano", "Ferrari-599-GTB-Fiorano.png", Color.RED, LocalDate.of(2015, 05, 02));
        Car car2 = new Car("Audi", "A6", "audi_a6_navyblue.png", Color.NAVY_BLUE, LocalDate.of(2020, 01, 03));
        Car car3 = new Car("Aston Martin", "DB5", "ambd5_silver.png", Color.SILVER, LocalDate.of(1964, 02, 01));
        Car car4 = new Car("BMW", "M3", "bmw_m3.png", Color.BLUE, LocalDate.of(2018, 02, 01));
        Car car5 = new Car("Mercedes Benz", "CLS 350", "mercedes_red.png", Color.RED, LocalDate.of(2013, 8, 01));
        Car car6 = new Car("Aston Martin", "DB10", "ambd10_black.png", Color.BLACK, LocalDate.of(2015, 9, 8));

        cars = Arrays.asList(car1, car2, car3, car4, car4, car5, car6);
        cars.forEach(car -> carRepository.save(car));
    }

    @Override
    public Car mappingCarDtoToEntity(CarDto dto) throws InvocationTargetException, IllegalAccessException {
        return (Car) utils.mapperFromDtoToEntity(dto);
    }

//    @Override
//    public Page<Car> findAllCars(Specification<Car> specification, Pageable pageable){
//        return carRepository.findAll(specification, pageable);
//    }

    @Transactional(readOnly = true)
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Transactional(readOnly = true)
    public Optional<Car> getById(Long id) {
        Optional<Car> carFromDataBase = carRepository.findById(id);
//                .orElseThrow(() -> new CarNotFoundException(id));
        return carFromDataBase;

    }

    public List<Car> findCarsByColor(Color color) {
        return carRepository.findCarsByColor(color).stream().collect(Collectors.toList());
    }

    public List<Car> findByMark(String mark) {
        return carRepository.findAllByMark(mark);
    }

    public void editCar(String mark, String model, String imageName, Color color, LocalDate dateOfProduction, Long id) {
        carRepository.editCar(mark, model, imageName, color.name(), dateOfProduction, id);
    }

    public void changeColor(Color color, Long id) {
        carRepository.changeColor(color.name(), id);
    }

    public void removeById(Long id) {
//        Optional<Car> findCar = getById(id);
//        if (findCar.isPresent()) {
        carRepository.deleteById(id);
//        }
    }
}
