package com.sinter.cookspire.service;

import java.util.List;

import com.sinter.cookspire.dto.NotficationRequestDTO;
import com.sinter.cookspire.dto.ResponseDTO;

import jakarta.validation.Valid;

public interface NotificationService {

    NotficationRequestDTO persistNotification(NotficationRequestDTO request);

    List<NotficationRequestDTO> fetchAllNotification(@Valid long userId);

    ResponseDTO deleteNotification(@Valid long userId);

}
