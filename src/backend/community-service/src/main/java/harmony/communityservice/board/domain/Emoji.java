package harmony.communityservice.board.domain;

import harmony.communityservice.common.exception.DuplicatedEmojiException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "emoji", indexes = @Index(name = "idx__boardId__emojiType", columnList = "board_id, emoji_type"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emoji {

    @Id
    @Column(name = "emoji_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emojiId;

    @NotNull
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "emoji_type")
    private Long emojiType;

    @ElementCollection
    @CollectionTable(name = "emoji_user",
            joinColumns = @JoinColumn(name = "emoji_id"))
    private Set<Long> userIds = new HashSet<>();

    @Builder
    public Emoji(Long boardId, Long emojiType, Long userId) {
        this.boardId = boardId;
        this.emojiType = emojiType;
        updateUserIds(userId);
    }

    public void updateUserIds(long userId) {
        this.userIds = this.userIds == null ? new HashSet<>() : this.userIds;
        Set<Long> newUserIds = new HashSet<>(this.userIds);
        newUserIds.add(userId);
        this.userIds = newUserIds;
    }

    public void deleteUserId(long userId) {
        exist(userId)
                .ifPresent(id -> {
                    this.userIds.remove(id);
                    this.userIds = new HashSet<>(this.userIds);
                });
    }

    public boolean exists(Long userId) {
        exist(userId)
                .ifPresent(e -> {
                    throw new DuplicatedEmojiException();
                });
        updateUserIds(userId);
        return true;
    }

    private Optional<Long> exist(long userId) {
        return userIds
                .stream()
                .filter(id -> Objects.equals(id, userId))
                .findAny();
    }
}
