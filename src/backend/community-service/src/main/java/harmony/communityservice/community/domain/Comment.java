package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment", indexes = @Index(name = "idx__boardId", columnList = "board_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board;

    @NotBlank
    @Column(name = "content")
    private String comment;

    @Embedded
    private WriterInfo writerInfo;

    @Embedded
    private CreationTime creationTime;

    @Embedded
    private ModifiedInfo modifiedInfo;

    @Builder
    public Comment(Board board, String comment, Long writerId, String writerName, String writerProfile) {
        this.board = board;
        this.comment = comment;
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

    public void modify(String comment) {
        this.comment = comment;
        this.modifiedInfo = this.modifiedInfo.modify();
    }
}
