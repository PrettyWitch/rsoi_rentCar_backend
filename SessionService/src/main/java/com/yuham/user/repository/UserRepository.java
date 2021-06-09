package com.yuham.user.repository;

import com.yuham.user.enetity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author yuhan
 * @date 06.05.2021 - 19:44
 * @purpose
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u from User u where u.userUid = :userUid")
    public Optional<User> findByUserUid(@Param("userUid") int userUid);

    @Query(value = "select u from User u where u.name = :name")
    public Optional<User> findByUserName(@Param("name") String name);
}
