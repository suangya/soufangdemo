package com.soufang.soufangdemo.repository;

import com.soufang.soufangdemo.entity.Role;
import com.soufang.soufangdemo.entity.Subway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findAllByUserId (Long userId);
}
