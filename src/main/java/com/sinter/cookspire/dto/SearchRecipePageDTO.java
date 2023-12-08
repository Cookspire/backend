package com.sinter.cookspire.dto;

import lombok.Data;

@Data
public class SearchRecipePageDTO {
 
    private String query;

    private int currentPageNumber;
}
