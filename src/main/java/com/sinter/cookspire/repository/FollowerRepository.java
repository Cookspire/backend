package com.sinter.cookspire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Follower;
import com.sinter.cookspire.entity.Users;

@Transactional
public interface FollowerRepository extends JpaRepository<Follower, Long> {

    Optional<Follower> findByFollowerUsersAndFolloweeUsers(Users users, Users users2);

    List<Follower> findAllByFollowerUsers(Users users);

    List<Follower> findAllByFolloweeUsers(Users users);

    @Query(nativeQuery = true, value = "select count(*) from follower where user_id=:userId")
    Long countUserFollowers(@Param(value = "userId") long userId);

    @Query(nativeQuery = true, value = "select count(*) from follower where follower_id=:userId")
    Long countUserFollowing(@Param(value = "userId") long userId);

}
