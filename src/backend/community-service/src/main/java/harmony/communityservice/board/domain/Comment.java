package harmony.communityservice.board.domain;

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
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment", indexes = @Index(name = "idx__boardId", columnList = "board_id"))
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotBlank
    @Column(name = "content")
    private String comment;

    @NotNull
    @Column(name = "board_id")
    private Long boardId;

    @Embedded
    private WriterInfo writerInfo;

    @Embedded
    private CreationTime creationTime;

    @Embedded
    private ModifiedInfo modifiedInfo;

    @Version
    private Long version;

    @Builder
    public Comment(String comment, Long writerId, String writerName, String writerProfile, Long boardId) {
        this.comment = comment;
        this.boardId = boardId;
        this.writerInfo = makeWriterInfo(writerId, writerName, writerProfile);
        this.modifiedInfo = new ModifiedInfo();
        this.creationTime = new CreationTime();
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
        this.modifiedInfo = this.modifiedInfo.modify();
    }
}
