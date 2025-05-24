package org.example.data.repository;

import org.example.data.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {


    void deleteAllByUserId(Long userId);
}
