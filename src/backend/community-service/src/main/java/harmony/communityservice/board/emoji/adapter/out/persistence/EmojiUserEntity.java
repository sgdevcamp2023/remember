package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.board.emoji.adapter.out.persistence.EmojiUserIdJpaVO.EmojiUserIdJavaType;
import harmony.communityservice.common.domain.DomainEntity;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@Table(name = "emoji_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmojiUserEntity extends DomainEntity<EmojiUserEntity, EmojiUserIdJpaVO> {

    @Id
    @Column(name = "emoji_user_id")
    @JavaType(EmojiUserIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private EmojiUserIdJpaVO emojiUserId;

    @Getter
    @NotNull
    @Embedded
    @Column(name = "user_id")
    private UserIdJpaVO userId;

    private EmojiUserEntity(UserIdJpaVO userId) {
        this.userId = userId;
    }

    public static EmojiUserEntity make(UserIdJpaVO userId) {
        return new EmojiUserEntity(userId);
    }

    @Override
    public EmojiUserIdJpaVO getId() {
        return emojiUserId;
    }
}
