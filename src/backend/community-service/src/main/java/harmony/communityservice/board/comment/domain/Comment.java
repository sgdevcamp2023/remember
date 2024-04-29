package harmony.communityservice.board.comment.domain;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.board.domain.BoardId.BoardIdJavaType;
import harmony.communityservice.board.comment.domain.CommentId.CommentIdJavaType;
import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.generic.WriterInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment", indexes = @Index(name = "idx__boardId", columnList = "board_id"))
public class Comment extends AggregateRoot<Comment, CommentId> {

    @Id
    @Column(name = "comment_id")
    @JavaType(CommentIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private CommentId commentId;

    @NotBlank
    @Column(name = "content")
    private String comment;

    @Column(name = "board_id")
    @JavaType(BoardIdJavaType.class)
    private BoardId boardId;

    @Embedded
    private WriterInfo writerInfo;

    @Version
    private Long version;

    @Builder
    public Comment(String comment, Long writerId, String writerName, String writerProfile, BoardId boardId) {
        this.comment = comment;
        this.boardId = boardId;
        this.writerInfo = makeWriterInfo(writerId, writerName, writerProfile);
    }

    private WriterInfo makeWriterInfo(Long writerId, String writerName, String writerProfile) {
        return WriterInfo.make(writerName, writerId, writerProfile);
    }

    public void verifyWriter(Long writerId) {
        writerInfo.verifyWriter(writerId);
    }

    public void modify(Long userId, String comment) {
        verifyWriter(userId);
        this.comment = comment;
        super.updateType();
    }

    @Override
    public CommentId getId() {
        return commentId;
    }
}
