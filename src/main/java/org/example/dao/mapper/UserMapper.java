package org.example.dao.mapper;

import org.example.dao.dto.response.ResponseUserDTO;
import org.example.dao.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    ResponseUserDTO toResponseUserDTO(User user);
}
