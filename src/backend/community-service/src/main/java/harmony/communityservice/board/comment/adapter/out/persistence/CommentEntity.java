package harmony.communityservice.board.comment.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO.BoardIdJavaType;
import harmony.communityservice.board.comment.adapter.out.persistence.CommentIdJpaVO.CommentIdJavaType;
import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.common.generic.WriterInfoJpaVO;
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
public class CommentEntity extends AggregateRoot<CommentEntity, CommentIdJpaVO> {

    @Id
    @Column(name = "comment_id")
    @JavaType(CommentIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private CommentIdJpaVO commentId;

    @NotBlank
    @Column(name = "content")
    private String comment;

    @Column(name = "board_id")
    @JavaType(BoardIdJavaType.class)
    private BoardIdJpaVO boardId;

    @Embedded
    private WriterInfoJpaVO writerInfo;

    @Version
    private Long version;

    @Builder
    public CommentEntity(String comment, Long writerId, String writerName, String writerProfile, BoardIdJpaVO boardId) {
        this.comment = comment;
        this.boardId = boardId;
        this.writerInfo = makeWriterInfo(writerId, writerName, writerProfile);
    }

    private WriterInfoJpaVO makeWriterInfo(Long writerId, String writerName, String writerProfile) {
        return WriterInfoJpaVO.make(writerName, writerId, writerProfile);
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
    public CommentIdJpaVO getId() {
        return commentId;
    }
}
