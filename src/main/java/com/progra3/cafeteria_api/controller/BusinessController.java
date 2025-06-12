package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.BusinessRequestDTO;
import com.progra3.cafeteria_api.model.dto.BusinessResponseDTO;
import com.progra3.cafeteria_api.service.impl.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/businesses")
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping
    public ResponseEntity<BusinessResponseDTO> create(@RequestBody @Valid BusinessRequestDTO dto) {
        BusinessResponseDTO responseDTO = businessService.createBusiness(dto);
        return ResponseEntity
                .created(URI.create("/api/businesses/" + responseDTO.id()))
                .body(responseDTO);
    }
}
