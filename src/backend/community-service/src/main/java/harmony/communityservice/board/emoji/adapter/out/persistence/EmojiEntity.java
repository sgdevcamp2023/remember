package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO.BoardIdJavaType;
import harmony.communityservice.board.emoji.adapter.out.persistence.EmojiIdJpaVO.EmojiIdJavaType;
import harmony.communityservice.common.domainentity.AggregateRootEntity;
import harmony.communityservice.common.exception.DuplicatedEmojiException;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "emoji", indexes = @Index(name = "idx__boardId__emojiType", columnList = "board_id, emoji_type"))
public class EmojiEntity extends AggregateRootEntity<EmojiEntity, EmojiIdJpaVO> {

    @Id
    @Column(name = "emoji_id")
    @JavaType(EmojiIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private EmojiIdJpaVO emojiId;

    @NotNull
    @Column(name = "board_id")
    @JavaType(BoardIdJavaType.class)
    private BoardIdJpaVO boardId;

    @NotNull
    @Column(name = "emoji_type")
    private Long emojiType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "emoji_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<EmojiUserEntity> emojiUsers = new ArrayList<>();

    @Builder
    public EmojiEntity(BoardIdJpaVO boardId, Long emojiType, EmojiUserEntity emojiUser) {
        this.boardId = boardId;
        this.emojiType = emojiType;
        updateEmojiUsers(emojiUser);
    }

    public void updateEmojiUsers(EmojiUserEntity emojiUser) {
        emojiUsers.add(emojiUser);
        super.updateType();
    }

    public void exists(UserIdJpaVO userId) {
        exist(userId)
                .ifPresent(e -> {
                    throw new DuplicatedEmojiException();
                });
        updateEmojiUsers(EmojiUserEntity.make(userId));
    }

    private Optional<UserIdJpaVO> exist(UserIdJpaVO userId) {
        return emojiUsers
                .stream()
                .map(EmojiUserEntity::getUserId)
                .filter(id -> Objects.equals(id, userId))
                .findAny();
    }

    @Override
    public EmojiIdJpaVO getId() {
        return emojiId;
    }
}
