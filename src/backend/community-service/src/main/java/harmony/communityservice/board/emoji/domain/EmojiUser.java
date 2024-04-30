package harmony.communityservice.board.emoji.domain;

import harmony.communityservice.board.emoji.domain.EmojiUserId.EmojiUserIdJavaType;
import harmony.communityservice.common.domain.DomainEntity;
import harmony.communityservice.user.domain.UserId;
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
public class EmojiUser extends DomainEntity<EmojiUser, EmojiUserId> {

    @Id
    @Column(name = "emoji_user_id")
    @JavaType(EmojiUserIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private EmojiUserId emojiUserId;

    @Getter
    @NotNull
    @Embedded
    @Column(name = "user_id")
    private UserId userId;

    private EmojiUser(UserId userId) {
        this.userId = userId;
    }

    public static EmojiUser make(UserId userId) {
        return new EmojiUser(userId);
    }

    @Override
    public EmojiUserId getId() {
        return emojiUserId;
    }
}
