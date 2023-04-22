package com.soufang.soufangdemo.repository;

import com.soufang.soufangdemo.entity.HouseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface HouseDetailRepository extends JpaRepository<HouseDetail,Long> {
}
