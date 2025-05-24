package org.example.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.data.dto.response.ResponseUserDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEmailDataDTO {

    String email;

    ResponseUserDTO user;

}
