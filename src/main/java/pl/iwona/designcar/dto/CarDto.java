package pl.iwona.designcar.dto;

import java.time.LocalDate;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.iwona.designcar.model.Car;
import pl.iwona.designcar.model.Color;

@Data
@NoArgsConstructor
public class CarDto {

    private Long id;
    @NotBlank(message = "Marka cannot be empty!")
    private String mark;
    @NotBlank(message = "Model cannot be empty!")
    private String model;
    private String imageName;
    @Enumerated(EnumType.STRING)
    private Color color;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfProduction;

    public CarDto(String mark, String model, String imageName, Color color, LocalDate dateOfProduction) {
        this.mark = mark;
        this.model = model;
        this.imageName = imageName;
        this.color = color;
        this.dateOfProduction = dateOfProduction;
    }

    public CarDto(Car car) {
        id = car.getId();
        mark = car.getMark();
        model = car.getModel();
        imageName = car.getImageName();
        color = car.getColor();
        dateOfProduction = car.getDateOfProduction();
    }
}
