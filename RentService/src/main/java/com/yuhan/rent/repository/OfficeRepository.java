package com.yuhan.rent.repository;

import com.yuhan.rent.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author yuhan
 * @date 06.05.2021 - 18:52
 * @purpose
 */
public interface OfficeRepository extends JpaRepository<Office, Integer> {

    public Optional<Office> findByOfficeUid(int officeUid);

}
