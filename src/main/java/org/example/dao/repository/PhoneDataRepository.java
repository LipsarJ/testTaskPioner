package org.example.dao.repository;

import org.example.dao.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    void deleteAllByUserId(Long userId);
}
