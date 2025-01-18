package org.zerock.knock.dto.dto.category;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.zerock.knock.dto.Enum.CategoryLevelOne;

@Data
public class CATEGORY_LEVEL_TWO_DTO {

    private String id;
    private String nm;
    @Enumerated(EnumType.STRING)
    private CategoryLevelOne parentNm;
    private Iterable<String> favoriteUsers;

}
