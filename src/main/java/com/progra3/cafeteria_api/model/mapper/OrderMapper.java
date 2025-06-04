package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {ItemMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "customerName", expression = "java(order.getCustomer() != null ? order.getCustomer().getName() : null)")
    @Mapping(target = "employeeName", expression = "java(order.getEmployee() != null ? order.getEmployee().getName() : null)")
    @Mapping(target = "seatingNumber", expression = "java(order.getSeating() != null ? order.getSeating().getNumber() : null)")
    @Mapping(target = "orderType", source = "type")
    OrderResponseDTO toDTO(Order order);
    @Mapping(target = "type", source = "orderType")
    Order toEntity(OrderRequestDTO orderRequestDTO, @Context Employee employee, @Context Customer customer, @Context Seating seating, @Context Business business);

    @BeforeMapping
    default void assignItem(@MappingTarget Order order, @Context List<Item> items) {
        order.setItems(items);
    }

    @BeforeMapping
    default void assignEmployee(@MappingTarget Order order, @Context Employee employee) {
        order.setEmployee(employee);
    }

    @BeforeMapping
    default void assignCustomer(@MappingTarget Order order, @Context Customer customer) {
        order.setCustomer(customer);
    }

    @BeforeMapping
    default void assignSeating(@MappingTarget Order order, @Context Seating seating) {
        order.setSeating(seating);
    }

    @BeforeMapping
    default void assignBusiness(@MappingTarget Order order, @Context Business business) {
        order.setBusiness(business);
    }
}
