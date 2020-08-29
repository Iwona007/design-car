package pl.iwona.designcar.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car implements Serializable {

    private static final long serialVersionUID = 3683778473783051508L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Mark  cannot be empty!")
    private String mark;
    @NotBlank(message = "Model cannot be empty!")
    private String model;
    @Column(name = "image_name")
    private String imageName;

    @JsonEnumDefaultValue
    @Enumerated(value = EnumType.STRING)
    private Color color;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_production")
    private LocalDate dateOfProduction;

    private String countryOfProduction;

    public Car(String mark, String model, String imageName, Color color, LocalDate dateOfProduction) {
        this.mark = mark;
        this.model = model;
        this.imageName = imageName;
        this.color = color;
        this.dateOfProduction = dateOfProduction;
    }

    public Car(Long id, String mark, String model, Color color, LocalDate dateOfProduction, String countryOfProduction) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.dateOfProduction = dateOfProduction;
        this.countryOfProduction = countryOfProduction;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append(getId());
        stringBuilder.append("; ");
        stringBuilder.append(getMark());
        stringBuilder.append("; ");
        stringBuilder.append(getModel());
        stringBuilder.append("; ");
        stringBuilder.append(getColor());
        stringBuilder.append(getDateOfProduction());
        stringBuilder.append("; ");
        stringBuilder.append(getCountryOfProduction());
        stringBuilder.append("; ");
        stringBuilder.length();
        return stringBuilder.toString();
    }
}
