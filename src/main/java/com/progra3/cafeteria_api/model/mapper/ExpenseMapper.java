package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseUpdateDTO;
import com.progra3.cafeteria_api.model.entity.Business;
import com.progra3.cafeteria_api.model.entity.Expense;
import com.progra3.cafeteria_api.model.entity.Supplier;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {SupplierMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExpenseMapper {
    @Mapping(target = "date", source = "dateTime")
    ExpenseResponseDTO toDTO(Expense expense);
    Expense toEntity(ExpenseRequestDTO expenseRequestDTO, @Context Supplier supplier, @Context Business business);
    void updateExpenseFromDTO(ExpenseUpdateDTO expenseUpdateDTO, @MappingTarget Expense expense);

    @BeforeMapping
    default void assignSupplier(@MappingTarget Expense expense, @Context Supplier supplier) {
        expense.setSupplier(supplier);
    }

    @BeforeMapping
    default void assignBusiness(@MappingTarget Expense expense, @Context Business business) {
        expense.setBusiness(business);
    }
}
