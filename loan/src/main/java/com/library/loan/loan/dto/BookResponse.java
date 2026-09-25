package com.library.loan.loan.dto;

import lombok.Data;

@Data
public class BookResponse {
    private long id;
    private int copiesTotal;
    private int copiesAvailable;
}

