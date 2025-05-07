package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.exception.OrderNotFoundException;
import com.progra3.cafeteria_api.model.dto.ItemRequestDTO;
import com.progra3.cafeteria_api.model.dto.ItemResponseDTO;
import com.progra3.cafeteria_api.model.dto.OrderRequestDTO;
import com.progra3.cafeteria_api.model.dto.OrderResponseDTO;
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
                .created(URI.create("/api/orders/" + responseDTO.getId()))
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

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<ItemResponseDTO>> getItems(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getItems(orderId));
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<ItemResponseDTO> addItem(
            @PathVariable Long orderId,
            @RequestBody ItemRequestDTO itemDTO) {
        ItemResponseDTO itemResponseDTO = orderService.addItem(orderId, itemDTO);
        return ResponseEntity
                .created(URI.create("/api/orders/{orderId}/items/" + itemResponseDTO.getId()))
                .body(itemResponseDTO);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            @RequestBody ItemRequestDTO itemDTO) {
        return ResponseEntity.ok(orderService.updateItem(orderId, itemId, itemDTO));
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        orderService.removeItem(orderId, itemId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}/discount")
    public ResponseEntity<Void> applyDiscount(
            @PathVariable Long orderId,
            @RequestParam Double discount) {
        orderService.applyDiscount(orderId, discount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/finalize")
    public ResponseEntity<OrderResponseDTO> finalizeOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.finalizeOrder(orderId));
    }

    @PostMapping("/{orderId}/delete")
    public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancel(orderId));
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

}
