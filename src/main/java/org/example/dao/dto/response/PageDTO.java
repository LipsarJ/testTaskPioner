package org.example.dao.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Страница с информацией о сущностях")
public class PageDTO<T> {
    private List<T> info;
    private long totalCount;
    private int page;
    private int countValuesPerPage;
}
