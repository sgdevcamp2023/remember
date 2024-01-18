package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "emoji")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emoji {

    @Id
    @Column(name = "emoji_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emojiId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "emoji_type")
    private Long emojiType;

    @OneToMany(mappedBy = "emoji", fetch = FetchType.LAZY)
    private List<EmojiUser> emojiUsers = new ArrayList<>();

    @Builder
    public Emoji(Board board, Long emojiType) {
        this.board = board;
        this.emojiType = emojiType;
    }
}
