package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.ExpenseRequestDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseUpdateDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.entity.Business;
import com.progra3.cafeteria_api.model.entity.Expense;
import com.progra3.cafeteria_api.model.entity.Supplier;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-03T22:01:56-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ExpenseMapperImpl implements ExpenseMapper {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public ExpenseResponseDTO toDTO(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        LocalDateTime date = null;
        Long id = null;
        SupplierResponseDTO supplier = null;
        Double amount = null;
        String comment = null;
        Boolean deleted = null;

        date = expense.getDateTime();
        id = expense.getId();
        supplier = supplierMapper.toDTO( expense.getSupplier() );
        amount = expense.getAmount();
        comment = expense.getComment();
        deleted = expense.getDeleted();

        ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO( id, supplier, amount, comment, date, deleted );

        return expenseResponseDTO;
    }

    @Override
    public Expense toEntity(ExpenseRequestDTO expenseRequestDTO, Supplier supplier, Business business) {
        if ( expenseRequestDTO == null ) {
            return null;
        }

        Expense expense = new Expense();

        assignSupplier( expense, supplier );
        assignBusiness( expense, business );

        expense.setAmount( expenseRequestDTO.amount() );
        expense.setComment( expenseRequestDTO.comment() );

        return expense;
    }

    @Override
    public void updateExpenseFromDTO(ExpenseUpdateDTO expenseUpdateDTO, Expense expense) {
        if ( expenseUpdateDTO == null ) {
            return;
        }

        if ( expenseUpdateDTO.amount() != null ) {
            expense.setAmount( expenseUpdateDTO.amount() );
        }
        if ( expenseUpdateDTO.comment() != null ) {
            expense.setComment( expenseUpdateDTO.comment() );
        }
    }
}
