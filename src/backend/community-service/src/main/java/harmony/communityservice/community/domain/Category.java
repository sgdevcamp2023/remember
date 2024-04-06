package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "guild_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Guild guild;

    @NotBlank
    private String name;

    @Embedded
    private CreationTime creationTime;

    @Embedded
    private ModifiedInfo modifiedInfo;

    @Builder
    public Category(Guild guild, String name) {
        this.guild = guild;
        this.name = name;
        this.creationTime = new CreationTime();
        this.modifiedInfo = new ModifiedInfo();
    }

    public void modifyName(String name) {
        this.name = name;
        this.modifiedInfo = modifiedInfo.modify();
    }

}
