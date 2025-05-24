package org.example.data.mapper;

import org.example.data.dto.response.ResponseUserDTO;
import org.example.data.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    ResponseUserDTO toResponseUserDTO(User user);
}
