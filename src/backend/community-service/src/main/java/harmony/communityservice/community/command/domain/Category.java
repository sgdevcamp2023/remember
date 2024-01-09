package harmony.communityservice.community.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;

    private String name;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

}
