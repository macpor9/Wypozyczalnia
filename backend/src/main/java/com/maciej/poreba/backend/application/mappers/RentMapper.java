package com.maciej.poreba.backend.application.mappers;

import com.maciej.poreba.backend.application.model.Rent;
import com.maciej.poreba.backend.application.payload.response.RentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentMapper {
    RentMapper INSTANCE = Mappers.getMapper(RentMapper.class);

    RentResponse rentToRentDto(Rent rent);
}
