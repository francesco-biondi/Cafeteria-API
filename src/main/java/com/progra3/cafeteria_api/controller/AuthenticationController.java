package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.LoginRequestDTO;
import com.progra3.cafeteria_api.model.dto.LoginResponseDTO;
import com.progra3.cafeteria_api.service.port.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO){
        LoginResponseDTO response = authenticationService.login(requestDTO);
        return ResponseEntity.ok(response);
    }
}
