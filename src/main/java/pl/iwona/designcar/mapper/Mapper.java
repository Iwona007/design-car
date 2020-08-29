package pl.iwona.designcar.mapper;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Component;
import pl.iwona.designcar.dto.CarDto;
import pl.iwona.designcar.model.Car;


@Component
public class Mapper {

    public Object mapperFromDtoToEntity(Object obj) throws IllegalAccessException, InvocationTargetException {
        if (obj instanceof CarDto) {
            Car car = new Car();
            BeanUtilsBean.getInstance().copyProperties(car, obj);
            return car;
        } else {
            throw new IllegalAccessException();
        }
    }

    public Object mapperFromEntityToDto(Object obj) throws InvocationTargetException, IllegalAccessException {
        if (obj instanceof Car) {
            CarDto carDto = new CarDto();
            BeanUtilsBean.getInstance().copyProperties(carDto, obj);
            return carDto;
        } else {
            throw new IllegalAccessException();
        }
    }
}
