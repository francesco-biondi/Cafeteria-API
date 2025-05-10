package com.progra3.cafeteria_api.exception.handler;

import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseMessage {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}