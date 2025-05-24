package org.example.data.repository.specification;

import org.example.data.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpecification {

    public static Specification<User> nameLike(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(root.get("username"), name + "%");
    }

    public static Specification<User> emailEquals(String email) {
        return (root, query, cb) -> email == null ? null : cb.equal(root.join("emailDataSet").get("email"), email);
    }

    public static Specification<User> phoneEquals(String phone) {
        return (root, query, cb) -> phone == null ? null : cb.equal(root.join("phoneDataSet").get("number"), phone);
    }

    public static Specification<User> dateOfBirthAfter(LocalDate dob) {
        return (root, query, cb) -> dob == null ? null : cb.greaterThan(root.get("birthday"), java.sql.Date.valueOf(dob));
    }
}