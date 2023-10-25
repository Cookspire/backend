package com.sinter.cookspire.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.sinter.cookspire.dto.NotficationRequestDTO;
import com.sinter.cookspire.dto.ResponseDTO;
import com.sinter.cookspire.entity.Notification;
import com.sinter.cookspire.entity.Users;
import com.sinter.cookspire.exception.ApplicationException;
import com.sinter.cookspire.repository.NotificationRepository;
import com.sinter.cookspire.repository.UserRepository;
import com.sinter.cookspire.service.NotificationService;

import jakarta.validation.Valid;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    MessageSource msgSrc;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public NotficationRequestDTO persistNotification(NotficationRequestDTO request) {

        logger.info("Inside persist notification Logic");

        Notification notificationEntity = new Notification();

        List<Users> chkusers = userRepo.findUsers(request.getFromUser(), request.getToUser());

        if (chkusers.size() < 2) {
            logger.error("Incorrect User Id");
            logger.info("Exiting from persist Notification logic");
            throw new ApplicationException(msgSrc.getMessage("User.NOT_FOUND", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

        Optional<Notification> notificationExist = notificationRepo.findById(request.getId());
        if (notificationExist.isPresent()) {
            notificationEntity.setId(notificationExist.get().getId());
            notificationEntity.setCreatedOn(notificationExist.get().getCreatedOn());
        } else if (request.getId() == 0)
            notificationEntity.setCreatedOn(LocalDateTime.now());
        else {
            logger.error("Incorrect Notification Id");
            logger.info("Exiting from persist Notification logic");
            throw new ApplicationException(msgSrc.getMessage("Notification.NOT_FOUND", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

        notificationEntity.setComments(request.getComments());
        notificationEntity.setToUser(request.getToUser());
        notificationEntity.setFromUser(request.getFromUser());
        notificationEntity.setUpdatedOn(LocalDateTime.now());
        notificationEntity.setIsShown(request.getIsShown());

        long notificationId = notificationRepo.save(notificationEntity).getId();
        logger.info("Exiting from persist Notification logic");

        return new NotficationRequestDTO(notificationId, request.getFromUser(), request.getToUser(),
                notificationEntity.getCreatedOn(), notificationEntity.getUpdatedOn(), notificationEntity.getComments(),
                notificationEntity.getIsShown());

    }

    @Override
    public List<NotficationRequestDTO> fetchAllNotification(@Valid long userId) {
        logger.info("Entering fetch All Notification logic");
        List<NotficationRequestDTO> response = new ArrayList<NotficationRequestDTO>();

        Optional<Users> chkUser = userRepo.findById(userId);
        if (chkUser.isEmpty()) {
            logger.error("Incorrect User Id");
            logger.info("Exiting from fetch all Notification logic");
            throw new ApplicationException(msgSrc.getMessage("User.NOT_FOUND", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }
        List<Notification> notifications = notificationRepo.findAllByToUser(userId);
        for (var notification : notifications) {
            response.add(new NotficationRequestDTO(notification.getId(), notification.getFromUser(),
                    notification.getToUser(),
                    notification.getCreatedOn(), notification.getUpdatedOn(),
                    notification.getComments(),
                    notification.getIsShown()));
        }
        logger.info("Exit from fetch All Notification logic");
        return response;
    }

    @Override
    public ResponseDTO deleteNotification(@Valid long userId) {
        logger.info("Entering delete Notification logic");

        Optional<Users> chkUser = userRepo.findById(userId);
        if (chkUser.isEmpty()) {
            logger.error("Incorrect User Id");
            logger.info("Exiting from delete Notification logic");
            throw new ApplicationException(msgSrc.getMessage("User.NOT_FOUND", null, Locale.ENGLISH),
                    HttpStatus.NOT_FOUND);
        }

        List<Notification> notifications = notificationRepo.findAllByToUser(userId);
        for (var notification : notifications) {

        }
        logger.info("Exiting from delete Notification logic");

        return new ResponseDTO("Notification deleted.");

    }

}
