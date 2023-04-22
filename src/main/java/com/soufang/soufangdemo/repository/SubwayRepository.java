package com.soufang.soufangdemo.repository;

import com.soufang.soufangdemo.entity.Subway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface SubwayRepository extends JpaRepository<Subway,Long> {
    List<Subway> findByCityEnName(String city);

    Page<Subway> findAll(Pageable pageable);

    Object findByCityEnNameAndName(String cityEnName, String name);
}
