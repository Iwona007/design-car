package pl.iwona.designcar.controller;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.iwona.designcar.convert_to_enum.ConvertColor;
import pl.iwona.designcar.dto.CarDto;
import pl.iwona.designcar.model.Car;
import pl.iwona.designcar.service.CarService;

@CrossOrigin(origins = "https://design-car-angular.herokuapp.com", maxAge = 3600)
@RestController
@RequestMapping("/api/cars")
public class CarControllers {
    private CarService carService;
    private ConvertColor convertColor;

    public CarControllers(CarService carService, ConvertColor convertColor) {
        this.carService = carService;
        this.convertColor = convertColor;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> listAllCarsFromDataBAse = carService.getAllCars();
        return new ResponseEntity<>(listAllCarsFromDataBAse, HttpStatus.OK);
    }

    @PostMapping("/add")
//    @PreAutorize("hasRole('ADMIN')")
    public ResponseEntity<Car> save(@RequestBody CarDto carDto) throws InvocationTargetException,
            IllegalAccessException, URISyntaxException {
        Car carTransformedFromDto = carService.mappingCarDtoToEntity(carDto);
        Car result = carService.save(carTransformedFromDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Car> getById(@PathVariable Long id) {
        Optional<Car> carFromDataBase = carService.getById(id);
        return carFromDataBase.map(response -> ResponseEntity.ok().body(response)).
                orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/color/{color}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // ok
    public ResponseEntity<List<Car>> findByColor(@PathVariable String color) {
        List<Car> listColorCarsFromDataBase = carService.findCarsByColor(convertColor.convertToEnum(color));
        return new ResponseEntity<>(listColorCarsFromDataBase, HttpStatus.OK);
    }

    @GetMapping("/mark/{mark}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") //ok
    public ResponseEntity<List<Car>> findByMark(@PathVariable String mark) {
        List<Car> listMarkCarsFromDataBase = carService.findByMark(mark);
        return new ResponseEntity<>(listMarkCarsFromDataBase, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")  // ok
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Car> editCar(@PathVariable Long id, @RequestBody CarDto carDto) throws InvocationTargetException, IllegalAccessException {
        Car carTransformFromDto = carService.mappingCarDtoToEntity(carDto);
        carService.editCar(carTransformFromDto.getMark(), carTransformFromDto.getModel(),
                carTransformFromDto.getImageName(), convertColor.convertToEnum(carTransformFromDto.getColor().name()),
                carTransformFromDto.getDateOfProduction(), id);
        return ResponseEntity.ok().body(carTransformFromDto);
    }

    @PatchMapping("/color/{id}/{color}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") //ok wyjatek, // ok
    public ResponseEntity<Car> changeColor(@PathVariable @NotNull String color, @PathVariable @NotNull Long id) {
        carService.changeColor(convertColor.convertToEnum(color), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')") // ok
    public ResponseEntity<Car> removeById(@PathVariable Long id) {
        if (carService.getById(id).isPresent()) {
            carService.removeById(id);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
