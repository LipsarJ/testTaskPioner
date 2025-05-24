package org.example.data.mapper;

import org.example.data.dto.request.RequestEmailDataDTO;
import org.example.data.entity.EmailData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface EmailDataMapper {

    EmailData toEmailData(RequestEmailDataDTO requestEmailDataDTO);
}
