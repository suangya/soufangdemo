package com.soufang.soufangdemo.repository;

import com.soufang.soufangdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByName (String name);

    User findByPhoneNumber(String phoneNumber);

    User findByEmail(String email);

}
