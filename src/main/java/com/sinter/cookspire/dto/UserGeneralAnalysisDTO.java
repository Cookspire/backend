package com.sinter.cookspire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGeneralAnalysisDTO {

    private long postCount;

    private long followerCount;

    private long followingCount;

}
