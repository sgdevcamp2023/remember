package harmony.communityservice.board.emoji.domain;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.board.domain.BoardId.BoardIdJavaType;
import harmony.communityservice.board.emoji.domain.EmojiId.EmojiIdJavaType;
import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.common.exception.DuplicatedEmojiException;
import harmony.communityservice.user.domain.UserId;
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
@Table(name = "emoji", indexes = @Index(name = "idx__boardId__emojiType", columnList = "board_id, emoji_type"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emoji extends AggregateRoot<Emoji, EmojiId> {

    @Id
    @Column(name = "emoji_id")
    @JavaType(EmojiIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private EmojiId emojiId;

    @NotNull
    @Column(name = "board_id")
    @JavaType(BoardIdJavaType.class)
    private BoardId boardId;

    @NotNull
    @Column(name = "emoji_type")
    private Long emojiType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "emoji_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<EmojiUser> emojiUsers = new ArrayList<>();

    @Builder
    public Emoji(BoardId boardId, Long emojiType, EmojiUser emojiUser) {
        this.boardId = boardId;
        this.emojiType = emojiType;
        updateEmojiUsers(emojiUser);
    }

    public void updateEmojiUsers(EmojiUser emojiUser) {
        emojiUsers.add(emojiUser);
    }

    public void exists(UserId userId) {
        exist(userId)
                .ifPresent(e -> {
                    throw new DuplicatedEmojiException();
                });
        updateEmojiUsers(EmojiUser.make(userId));
    }

    private Optional<UserId> exist(UserId userId) {
        return emojiUsers
                .stream()
                .map(EmojiUser::getUserId)
                .filter(id -> Objects.equals(id, userId))
                .findAny();
    }

    @Override
    public EmojiId getId() {
        return emojiId;
    }
}
