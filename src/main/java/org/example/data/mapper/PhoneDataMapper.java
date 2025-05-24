package org.example.data.mapper;

import org.example.data.dto.request.RequestPhoneDataDTO;
import org.example.data.entity.PhoneData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface PhoneDataMapper {

    PhoneData toPhoneData(RequestPhoneDataDTO requestPhoneDataDTO);
}
