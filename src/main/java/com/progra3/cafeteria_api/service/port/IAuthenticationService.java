package com.progra3.cafeteria_api.service.port;

import com.progra3.cafeteria_api.model.dto.LoginRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;

public interface IAuthenticationService {

    EmployeeResponseDTO login(LoginRequestDTO loginDTO);
    void logout();

}
