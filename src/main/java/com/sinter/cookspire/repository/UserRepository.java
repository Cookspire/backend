package com.sinter.cookspire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Users;

@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByEmailAndPassword(String email, String password);

    @Query(nativeQuery = true, value = "select * from users where id=:fromUser or id=:toUser")
    List<Users> findUsers(@Param("fromUser") long fromUser, @Param("toUser") long toUser);

    List<Users> findAllByIsVerifiedTrue();

    @Query(nativeQuery = true, value="select * from users where username ILIKE %:search%")
    List<Users> filterUsers(@Param("search") String search);

    @Query(nativeQuery = true, value="select * from users where id NOT in (select follower_id from follower where user_id= :user_id ) AND id<>:user_id LIMIT 3")
    List<Users> filterRandomFollowers(@Param("user_id") long user_id);

}
