package harmony.communityservice.board.domain;

import harmony.communityservice.board.board.dto.ModifyBoardRequest;
import harmony.communityservice.board.board.dto.SearchImageResponse;
import harmony.communityservice.board.board.dto.SearchImagesResponse;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board", indexes = @Index(name = "idx__channelId", columnList = "channel_id"))
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @NotNull
    @Column(name = "channel_id")
    private Long channelId;

    @Embedded
    private Content content;

    @Embedded
    private WriterInfo writerInfo;

    @Embedded
    private ModifiedInfo modifiedInfo;

    @Embedded
    private CreationTime creationTime;

    @Version
    private Long version;

    @OrderColumn(name = "image_idx")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "images",
            joinColumns = @JoinColumn(name = "board_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)))
    private List<Image> images;


    @Builder
    public Board(Long channelId, List<Image> images,
                 String title, String content, String writerName, Long writerId, String writerProfile) {
        this.channelId = channelId;
        this.images = images;
        this.content = makeContent(title, content);
        this.modifiedInfo = new ModifiedInfo();
        this.creationTime = new CreationTime();
        this.writerInfo = makeWriterInfo(writerName, writerId, writerProfile);
    }

    public void verifyWriter(Long writerId) {
        writerInfo.verifyWriter(writerId);
    }

    public void modifyTitleAndContent(ModifyBoardRequest modifyBoardRequest) {
        verifyWriter(modifyBoardRequest.userId());
        this.content = this.content.modify(modifyBoardRequest.title(), modifyBoardRequest.content());
        this.modifiedInfo = modifiedInfo.modify();
    }

    public SearchImagesResponse makeSearchImagesResponse() {
        return new SearchImagesResponse(
                this.images.stream()
                        .map(image -> new SearchImageResponse(image.getImageUrl()))
                        .collect(Collectors.toList())
        );
    }

    private Content makeContent(String title, String content) {
        return Content.make(title, content);
    }

    private WriterInfo makeWriterInfo(String writerName, Long writerId, String writerProfile) {
        return WriterInfo.make(writerName, writerId, writerProfile);
    }

}
