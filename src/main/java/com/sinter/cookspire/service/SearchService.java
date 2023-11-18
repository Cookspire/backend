package com.sinter.cookspire.service;

import com.sinter.cookspire.dto.SearchRequestDTO;

public interface SearchService {
    
    boolean searchCookspire(SearchRequestDTO request);
}
