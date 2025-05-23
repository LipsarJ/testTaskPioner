package org.example.service;

import org.example.dao.dto.request.RequestUserDTO;
import org.example.dao.dto.request.SignUpDTO;
import org.example.dao.dto.response.ResponseUserDTO;
import org.example.dao.repository.filter.FilterUserParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    ResponseUserDTO createUser(SignUpDTO signUpDTO);

    ResponseUserDTO updateUser(RequestUserDTO requestUserDTO);

    Page<ResponseUserDTO> getUsersWithFilterAndPagination(FilterUserParam filter, Pageable pageable);
}
