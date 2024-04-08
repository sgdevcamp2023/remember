package harmony.communityservice.community.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "emoji", indexes = @Index(name = "idx__boardId", columnList = "board_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emoji {

    @Id
    @Column(name = "emoji_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emojiId;

    @ManyToOne
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board;

    @Column(name = "emoji_type")
    private Long emojiType;

    @ElementCollection
    @CollectionTable(name = "emoji_user",
            joinColumns = @JoinColumn(name = "emoji_id"))
    private Set<Long> userIds = new HashSet<>();

    @Builder
    public Emoji(Board board, Long emojiType, Long userId) {
        this.board = board;
        this.emojiType = emojiType;
        updateUserIds(userId);
    }

    public void updateUserIds(long userId) {
        this.userIds = this.userIds == null ? new HashSet<>() : this.userIds;
        Set<Long> newUserIds = new HashSet<>(this.userIds);
        newUserIds.add(userId);
        this.userIds = newUserIds;
    }

    public Optional<Long> exist(long userId) {
        return userIds
                .stream()
                .filter(id -> Objects.equals(id, userId))
                .findAny();
    }

    public void deleteUserId(long userId) {
        exist(userId)
                .ifPresent(id -> {
                    this.userIds.remove(id);
                    this.userIds = new HashSet<>(this.userIds);
                });
    }
}
