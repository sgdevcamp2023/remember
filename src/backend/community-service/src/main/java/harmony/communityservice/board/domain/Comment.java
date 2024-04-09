package harmony.communityservice.board.domain;

import harmony.communityservice.board.comment.dto.ModifyCommentRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

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
    public Comment(String comment, Long writerId, String writerName, String writerProfile) {
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

    public void modify(ModifyCommentRequest modifyCommentRequest) {
        verifyWriter(modifyCommentRequest.userId());
        this.comment = modifyCommentRequest.comment();
        this.modifiedInfo = this.modifiedInfo.modify();
    }
}
