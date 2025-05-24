package org.example.service;

import org.example.data.dto.request.SignUpDTO;
import org.example.data.dto.request.UpdateUserDTO;
import org.example.data.dto.response.ResponseUserDTO;
import org.example.data.repository.filter.FilterUserParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    ResponseUserDTO createUser(SignUpDTO signUpDTO);

    ResponseUserDTO updateUser(UpdateUserDTO updateUserDTO);

    Page<ResponseUserDTO> getUsersWithFilterAndPagination(FilterUserParam filter, Pageable pageable);
}
