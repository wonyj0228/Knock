package org.zerock.knock.dto.document.category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.zerock.knock.dto.document.user.USER_INDEX;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Document(indexName = "category-level-two-index")
public class CATEGORY_LEVEL_TWO_INDEX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Field(type = FieldType.Text, analyzer = "nori", fielddata = true)
    private String nm;

    @Field(type = FieldType.Text, analyzer = "nori", fielddata = true)
    private String parentNm;

    @ManyToMany
    private Set<USER_INDEX> favoriteUsers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CATEGORY_LEVEL_TWO_INDEX that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(nm, that.nm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nm);
    }
}
