package com.progra3.cafeteria_api.model.mapper;

import com.progra3.cafeteria_api.model.dto.AddressResponseDTO;
import com.progra3.cafeteria_api.model.dto.AuditRequestDTO;
import com.progra3.cafeteria_api.model.dto.AuditResponseDTO;
import com.progra3.cafeteria_api.model.dto.ExpenseResponseDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.dto.SupplierResponseDTO;
import com.progra3.cafeteria_api.model.entity.Address;
import com.progra3.cafeteria_api.model.entity.Audit;
import com.progra3.cafeteria_api.model.entity.Expense;
import com.progra3.cafeteria_api.model.entity.Order;
import com.progra3.cafeteria_api.model.entity.Supplier;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-12T23:55:28-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class AuditMapperImpl implements AuditMapper {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public AuditResponseDTO toDTO(Audit audit) {
        if ( audit == null ) {
            return null;
        }

        Long id = null;
        LocalDateTime startTime = null;
        LocalDateTime closeTime = null;
        Double initialCash = null;
        List<OrderResponseDTO> orders = null;
        List<ExpenseResponseDTO> expenses = null;
        String auditStatus = null;
        Double totalExpensed = null;
        Double total = null;
        Double balanceGap = null;
        Boolean deleted = null;

        id = audit.getId();
        startTime = audit.getStartTime();
        closeTime = audit.getCloseTime();
        initialCash = audit.getInitialCash();
        orders = orderListToOrderResponseDTOList( audit.getOrders() );
        expenses = expenseListToExpenseResponseDTOList( audit.getExpenses() );
        if ( audit.getAuditStatus() != null ) {
            auditStatus = audit.getAuditStatus().name();
        }
        totalExpensed = audit.getTotalExpensed();
        total = audit.getTotal();
        balanceGap = audit.getBalanceGap();
        deleted = audit.getDeleted();

        AuditResponseDTO auditResponseDTO = new AuditResponseDTO( id, startTime, closeTime, initialCash, orders, expenses, auditStatus, totalExpensed, total, balanceGap, deleted );

        return auditResponseDTO;
    }

    @Override
    public Audit toEntity(AuditRequestDTO auditRequestDTO) {
        if ( auditRequestDTO == null ) {
            return null;
        }

        Audit audit = new Audit();

        audit.setInitialCash( auditRequestDTO.initialCash() );

        return audit;
    }

    protected List<OrderResponseDTO> orderListToOrderResponseDTOList(List<Order> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderResponseDTO> list1 = new ArrayList<OrderResponseDTO>( list.size() );
        for ( Order order : list ) {
            list1.add( orderMapper.toDTO( order ) );
        }

        return list1;
    }

    protected AddressResponseDTO addressToAddressResponseDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        String street = null;
        String city = null;
        String province = null;
        String zipCode = null;

        street = address.getStreet();
        city = address.getCity();
        province = address.getProvince();
        zipCode = address.getZipCode();

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO( street, city, province, zipCode );

        return addressResponseDTO;
    }

    protected SupplierResponseDTO supplierToSupplierResponseDTO(Supplier supplier) {
        if ( supplier == null ) {
            return null;
        }

        Long id = null;
        String legalName = null;
        String tradeName = null;
        String cuit = null;
        String phoneNumber = null;
        String email = null;
        AddressResponseDTO address = null;
        Boolean deleted = null;

        id = supplier.getId();
        legalName = supplier.getLegalName();
        tradeName = supplier.getTradeName();
        cuit = supplier.getCuit();
        phoneNumber = supplier.getPhoneNumber();
        email = supplier.getEmail();
        address = addressToAddressResponseDTO( supplier.getAddress() );
        deleted = supplier.getDeleted();

        SupplierResponseDTO supplierResponseDTO = new SupplierResponseDTO( id, legalName, tradeName, cuit, phoneNumber, email, address, deleted );

        return supplierResponseDTO;
    }

    protected ExpenseResponseDTO expenseToExpenseResponseDTO(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        Long id = null;
        SupplierResponseDTO supplier = null;
        Double amount = null;
        String comment = null;
        Boolean deleted = null;

        id = expense.getId();
        supplier = supplierToSupplierResponseDTO( expense.getSupplier() );
        amount = expense.getAmount();
        comment = expense.getComment();
        deleted = expense.getDeleted();

        LocalDateTime date = null;

        ExpenseResponseDTO expenseResponseDTO = new ExpenseResponseDTO( id, supplier, amount, comment, date, deleted );

        return expenseResponseDTO;
    }

    protected List<ExpenseResponseDTO> expenseListToExpenseResponseDTOList(List<Expense> list) {
        if ( list == null ) {
            return null;
        }

        List<ExpenseResponseDTO> list1 = new ArrayList<ExpenseResponseDTO>( list.size() );
        for ( Expense expense : list ) {
            list1.add( expenseToExpenseResponseDTO( expense ) );
        }

        return list1;
    }
}
