package com.library.media.media.dto;

import lombok.Data;

@Data
public class BookResponse {
    private long id;
    private int copiesTotal;
    private int copiesAvailable;
}

