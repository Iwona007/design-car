package pl.iwona.designcar.repository;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.iwona.designcar.model.Car;
import pl.iwona.designcar.model.Color;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE cars SET cars.mark = :mark, cars.model = :model, cars.image_name = :imageName, cars.color = :color," +
            "cars.date_of_production = :dateOfProduction WHERE cars.id = :id ", nativeQuery = true)
    void editCar(@Param("mark") String mark,
                 @Param("model") String model,
                 @Param("imageName") String imageName,
                 @Param("color") String color,
                 @Param("dateOfProduction") LocalDate dateOfProduction, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE cars SET cars.color = :color WHERE cars.id = :id", nativeQuery = true)
    void changeColor(@Param("color") String color, @Param("id") Long id);

    Car save(Car car);

    List<Car> findCarsByColor(Color color);

    List<Car> findAllByMark(String mark);
}

