package org.example.data.repository;

import org.example.data.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    void deleteAllByUserId(Long userId);
}
