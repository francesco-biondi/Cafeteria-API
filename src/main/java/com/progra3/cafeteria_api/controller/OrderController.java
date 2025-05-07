package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.exception.OrderModificationNotAllowedException;
import com.progra3.cafeteria_api.exception.OrderNotFoundException;
import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
import com.progra3.cafeteria_api.model.enums.OrderStatus;
import com.progra3.cafeteria_api.service.impl.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.progra3.cafeteria_api.exception.ItemNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO dto) {
        OrderResponseDTO responseDTO = orderService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/orders/" + responseDTO.id()))
                .body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id, @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.ok(orderService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateDiscount(
            @PathVariable Long id,
            @RequestParam Double discount) {
        orderService.updateDiscount(id, discount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemResponseDTO>> getItems(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getItems(id));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<ItemResponseDTO> addItem(
            @PathVariable Long id,
            @RequestBody ItemRequestDTO dto) {
        ItemResponseDTO itemResponseDTO = orderService.addItem(id, dto);
        return ResponseEntity
                .created(URI.create("/api/orders/{orderId}/items/" + itemResponseDTO.id()))
                .body(itemResponseDTO);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            @RequestBody ItemRequestDTO dto) {
        return ResponseEntity.ok(orderService.updateItem(orderId, itemId, dto));
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<ItemResponseDTO> removeItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(orderService.removeItem(orderId, itemId));
    }
    @PostMapping("/{orderId}/finalize")
    public ResponseEntity<OrderResponseDTO> finalizeOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, OrderStatus.FINALIZED));
    }

    @PostMapping("/{orderId}/bill")
    public ResponseEntity<OrderResponseDTO> billOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, OrderStatus.BILLED));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, OrderStatus.CANCELED));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFound(ItemNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleItemNotFound(OrderNotFoundException e) {
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

}
