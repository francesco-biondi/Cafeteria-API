package com.progra3.cafeteria_api.exception.handler;

import com.progra3.cafeteria_api.exception.audit.AuditInProgressException;
import com.progra3.cafeteria_api.exception.audit.AuditNotFoundException;
import com.progra3.cafeteria_api.exception.customer.CustomerAlreadyActiveException;
import com.progra3.cafeteria_api.exception.customer.CustomerNotFoundException;
import com.progra3.cafeteria_api.exception.expense.ExpenseNotFoundException;
import com.progra3.cafeteria_api.exception.order.ItemNotFoundException;
import com.progra3.cafeteria_api.exception.order.OrderModificationNotAllowedException;
import com.progra3.cafeteria_api.exception.order.OrderNotFoundException;
import com.progra3.cafeteria_api.exception.product.CategoryCannotBeDeletedException;
import com.progra3.cafeteria_api.exception.product.CategoryNotFoundException;
import com.progra3.cafeteria_api.exception.product.ProductNotFoundException;
import com.progra3.cafeteria_api.exception.supplier.SupplierNotFoundException;
import com.progra3.cafeteria_api.exception.user.*;
import com.progra3.cafeteria_api.exception.utilities.InvalidDateException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ResponseMessage response = ResponseMessage.builder()
                .message("Validation errors: " + errors)
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseMessage> handleSQLException(SQLException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class)))
    })
    public ResponseEntity<ResponseMessage> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class)))
    })
    public ResponseEntity<ResponseMessage> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(CategoryCannotBeDeletedException.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Cannot delete category", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class)))
    })
    public ResponseEntity<ResponseMessage> handleCannotDeleteCategoryException(CategoryCannotBeDeletedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleSupplierNotFoundException(SupplierNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(AuditNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleAuditNotFoundException(AuditNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(CustomerAlreadyActiveException.class)
    public ResponseEntity<ResponseMessage> handleCustomerActiveException(CustomerAlreadyActiveException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleExpenseNotFoundException(ExpenseNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ResponseMessage> handleInvalidDateException(InvalidDateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(AuditInProgressException.class)
    public ResponseEntity<ResponseMessage> handleAuditInProgressException(AuditInProgressException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFound(ItemNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFound(OrderNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(OrderModificationNotAllowedException.class)
    public ResponseEntity<String> handleOrderModificationNotAllowed(OrderModificationNotAllowedException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<ResponseMessage> handleAdminExists(AdminAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .build()
                );
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleEmployeeNotFound(EmployeeNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(EmployeePermissionException.class)
    public ResponseEntity<ResponseMessage> handleEmployeeCreationPermission(EmployeePermissionException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(EmployeeCannotBeDeletedPermission.class)
    public ResponseEntity<ResponseMessage> handleEmployeeDeletionPermission(EmployeeCannotBeDeletedPermission ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(AdminCannotBeDeletedException.class)
    public ResponseEntity<ResponseMessage> handleAdminDeletion(AdminCannotBeDeletedException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.CONTINUE.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ResponseMessage> handleInvalidPassword(InvalidPasswordException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.CONTINUE.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(EmployeeDeletedException.class)
    public ResponseEntity<ResponseMessage> handleDeletedEmployee(EmployeeDeletedException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(NoLoggedUserException.class)
    public ResponseEntity<ResponseMessage> handleNoLoggedUser(NoLoggedUserException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseMessage.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
