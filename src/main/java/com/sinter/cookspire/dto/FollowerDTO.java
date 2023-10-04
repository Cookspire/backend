package com.sinter.cookspire.dto;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowerDTO {

    @NonNull
    private long followerId;

    @NonNull
    private long followeeId;

    private boolean followUser;

}
