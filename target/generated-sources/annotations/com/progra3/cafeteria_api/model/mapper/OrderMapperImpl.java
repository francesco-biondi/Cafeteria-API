package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.entity.Customer;
import com.progra3.cafeteria_api.model.entity.Employee;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.Seating;
import com.progra3.cafeteria_api.model.enums.OrderType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T13:50:36-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public OrderResponseDTO toDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        Long id = null;
        List<ItemResponseDTO> items = null;
        LocalDateTime dateTime = null;
        Integer peopleCount = null;
        Integer discount = null;
        Double subtotal = null;
        Double total = null;

        id = order.getId();
        items = itemListToItemResponseDTOList( order.getItems() );
        dateTime = order.getDateTime();
        peopleCount = order.getPeopleCount();
        discount = order.getDiscount();
        subtotal = order.getSubtotal();
        total = order.getTotal();

        String status = order.getStatus().getName();
        String employeeName = null;
        String customerName = null;
        Integer seatingNumber = null;
        OrderType orderType = null;

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO( id, employeeName, customerName, seatingNumber, orderType, items, dateTime, peopleCount, discount, status, subtotal, total );

        return orderResponseDTO;
    }

    @Override
    public Order toEntity(OrderRequestDTO orderRequestDTO, Employee employee, Customer customer, Seating seating) {
        if ( orderRequestDTO == null ) {
            return null;
        }

        Order order = new Order();

        assignEmployee( order, employee );
        assignCustomer( order, customer );
        assignSeating( order, seating );

        order.setPeopleCount( orderRequestDTO.peopleCount() );

        return order;
    }

    protected List<ItemResponseDTO> itemListToItemResponseDTOList(List<Item> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemResponseDTO> list1 = new ArrayList<ItemResponseDTO>( list.size() );
        for ( Item item : list ) {
            list1.add( itemMapper.toDTO( item ) );
        }

        return list1;
    }
}
