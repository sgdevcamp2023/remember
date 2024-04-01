package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment", indexes = @Index(name = "idx__boardId",columnList = "board_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "board_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
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
    private String createdAt;

    @Column(name = "modified_at")
    private String modifiedAt;

    @Builder
    public Comment(Board board, String comment, Long userId, String writerName,String writerProfile) {
        this.board = board;
        this.comment = comment;
        this.userId = userId;
        this.writerName = writerName;
        this.modified = false;
        this.writerProfile = writerProfile;
        this.createdAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void verifyWriter(Long writerId) {
        if (!this.userId.equals(writerId)) {
            throw new IllegalStateException();
        }
    }

    public void modify(String comment) {
        this.comment = comment;
        this.modified = true;
        this.modifiedAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
