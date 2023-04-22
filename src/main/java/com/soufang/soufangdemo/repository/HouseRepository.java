package com.soufang.soufangdemo.repository;

import com.soufang.soufangdemo.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface HouseRepository extends JpaRepository<House, Long> {
}
