package org.example.common;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String msg;
}