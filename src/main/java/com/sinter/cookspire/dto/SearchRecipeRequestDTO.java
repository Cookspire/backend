package com.sinter.cookspire.dto;

import lombok.Data;

@Data
public class SearchRecipeRequestDTO {
    
    private String query;

    private String dietPlan;

    private int fromTime;

    private int toTime;

}
