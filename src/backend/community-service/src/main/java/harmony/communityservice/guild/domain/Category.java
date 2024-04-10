package harmony.communityservice.guild.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank
    @Column(name = "category_name")
    private String name;

    @Embedded
    private CreationTime creationTime;

    @Embedded
    private ModifiedInfo modifiedInfo;

    @Builder
    public Category(String name) {
        this.name = name;
        this.creationTime = new CreationTime();
        this.modifiedInfo = new ModifiedInfo();
    }

    public void modifyName(String name) {
        this.name = name;
        this.modifiedInfo = modifiedInfo.modify();
    }

}
