package org.example.dao.repository;

import org.example.dao.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {


    void deleteAllByUserId(Long userId);
}
