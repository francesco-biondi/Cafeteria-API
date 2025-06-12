package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.SeatingRequestDTO;
import com.progra3.cafeteria_api.model.dto.SeatingResponseDTO;
import com.progra3.cafeteria_api.model.entity.Business;
import com.progra3.cafeteria_api.model.entity.Seating;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SeatingMapper {
    SeatingResponseDTO toDTO(Seating seating);

    Seating toEntity(SeatingRequestDTO seatingRequestDTO, @Context Business business);

    Seating updateSeatingFromDTO(@MappingTarget Seating seating, SeatingRequestDTO seatingRequestDTO);

    @BeforeMapping
    default void assignBusiness(@MappingTarget Seating seating, @Context Business business) {
        seating.setBusiness(business);
    }
}
