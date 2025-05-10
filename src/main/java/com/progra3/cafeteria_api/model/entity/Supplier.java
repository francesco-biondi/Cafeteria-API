package com.progra3.cafeteria_api.model.entity;

import lombok.*;

@AllArgsConstructor
@Data
@Builder
public class Supplier{
    private Long id;
    private String legalName;
    private String tradeName;
    private String cuit;
    private String phoneNumber;
    private String email;
    private String address;
    private Boolean deleted;
}
