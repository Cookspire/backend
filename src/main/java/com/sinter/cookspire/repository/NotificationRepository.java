package com.sinter.cookspire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinter.cookspire.entity.Notification;

import jakarta.validation.Valid;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByToUser(@Valid long userId);

}
