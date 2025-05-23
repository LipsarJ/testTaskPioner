package org.example.dao.repository.filter;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FilterUserParam {

    @Nullable
    private String name;

    @Nullable
    private String email;

    @Nullable
    private String phone;

    @Nullable
    private LocalDate dateOfBirth;
}
