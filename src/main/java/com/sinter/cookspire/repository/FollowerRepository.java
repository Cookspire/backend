package com.sinter.cookspire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinter.cookspire.entity.Follower;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

}
