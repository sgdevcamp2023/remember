package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category", indexes = @Index(name = "idx__guildId", columnList = "guild_id"))
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotNull
    @Column(name = "guild_id")
    private Long guildId;

    @NotBlank
    @Column(name = "category_name")
    private String name;

    @Embedded
    private CreationTime creationTime;

    @Embedded
    private ModifiedInfo modifiedInfo;

    @Builder
    public Category(Long guildId, String name) {
        this.guildId = guildId;
        this.name = name;
        this.creationTime = new CreationTime();
        this.modifiedInfo = new ModifiedInfo();
    }

    public void modifyName(String name) {
        this.name = name;
        this.modifiedInfo = modifiedInfo.modify();
    }

}
