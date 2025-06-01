package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.model.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {OrderMapper.class, Expense.class})
public interface AuditMapper {
    AuditResponseDTO toDTO(Audit audit);
    Audit toEntity(AuditRequestDTO auditRequestDTO);

    @BeforeMapping
    default void assignOrders(@MappingTarget Audit audit, @Context List<Order> orders) {
        audit.setOrders(orders);
    }

    @BeforeMapping
    default void assignExpense(@MappingTarget Audit audit, @Context List<Expense> expenses) {
        audit.setExpenses(expenses);
    }
}
