package org.example.dao.repository;

import org.example.dao.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable(value = "users::email", key = "#email")
    @Query("select u from User u join u.emailDataSet e where e.email = :email")
    Optional<User> findByEmail(String email);

    @Cacheable(value = "users::id", key = "#id")
    @Override
    Optional<User> findById(Long id);

    @Query("select u from User u join u.emailDataSet e where e.email = :email")
    boolean existsByEmail(String email);

    @Query("select u from User u join u.phoneDataSet p where p.phone = :number")
    boolean existsByPhone(String number);

    @Query("select count(u) > 0 from User u join u.emailDataSet e where e.email = :email and u.id <> :id")
    boolean existsByEmailAndIdNot(String email, Long id);

    @Query("select count(u) > 0 from User u join u.phoneDataSet p where p.phone = :number and u.id <> :id")
    boolean existsByPhoneAndIdNot(String phone, Long id);

    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
