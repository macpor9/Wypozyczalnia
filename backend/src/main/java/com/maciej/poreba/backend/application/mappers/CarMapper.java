package com.maciej.poreba.backend.application.mappers;

import com.maciej.poreba.backend.application.model.Car;
import com.maciej.poreba.backend.application.dto.response.CarResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "reservedUntil", target = "availableDate")
    CarResponse carToCarResponse(Car car);
}

