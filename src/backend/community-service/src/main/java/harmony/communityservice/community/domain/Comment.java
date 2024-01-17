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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @NotBlank
    @Column(name = "content")
    private String comment;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Column(name = "writer_name")
    private String writerName;

    @NotBlank
    @Column(name = "writer_profile")
    private String writerProfile;

    @NotNull
    @Column(name = "modified")
    private boolean modified;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Builder
    public Comment(Board board, String comment, Long userId, String writerName,String writerProfile) {
        this.board = board;
        this.comment = comment;
        this.userId = userId;
        this.writerName = writerName;
        this.modified = false;
        this.writerProfile = writerProfile;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void checkWriter(Long writerId) {
        if (!this.userId.equals(writerId)) {
            throw new IllegalStateException();
        }
    }

    public void updateComment(String comment) {
        this.comment = comment;
        this.modified = true;
        this.modifiedAt = LocalDateTime.now();
    }
}
