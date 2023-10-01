package com.sinter.cookspire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Follower;
@Transactional
public interface FollowerRepository extends JpaRepository<Follower, Long> {

}
