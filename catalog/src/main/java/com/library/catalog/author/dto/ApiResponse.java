package com.library.catalog.author.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
}
