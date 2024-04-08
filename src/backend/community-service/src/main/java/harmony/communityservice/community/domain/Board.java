package harmony.communityservice.community.domain;

import harmony.communityservice.community.mapper.ToSearchCommentResponseMapper;
import harmony.communityservice.community.mapper.ToSearchEmojiResponseMapper;
import harmony.communityservice.community.query.dto.SearchCommentResponse;
import harmony.communityservice.community.query.dto.SearchCommentsResponse;
import harmony.communityservice.community.query.dto.SearchEmojisResponse;
import harmony.communityservice.community.query.dto.SearchImageResponse;
import harmony.communityservice.community.query.dto.SearchImagesResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Emoji> emojis = new ArrayList<>();


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

    private Content makeContent(String title, String content) {
        return Content.make(title, content);
    }

    private WriterInfo makeWriterInfo(String writerName, Long writerId, String writerProfile) {
        return WriterInfo.make(writerName, writerId, writerProfile);
    }

    public void verifyWriter(Long writerId) {
        writerInfo.verifyWriter(writerId);
    }

    public void modifyTitleAndContent(String title, String content) {
        this.content = this.content.modify(title, content);
        this.modifiedInfo = modifiedInfo.modify();
    }

    public SearchImagesResponse makeSearchImagesResponse() {
        return new SearchImagesResponse(
                this.images.stream()
                        .map(image -> new SearchImageResponse(image.getImageAddr()))
                        .collect(Collectors.toList())
        );
    }

    public SearchEmojisResponse makeSearchEmojisResponse() {
        return new SearchEmojisResponse(
                this.emojis.stream()
                        .map(ToSearchEmojiResponseMapper::convert)
                        .collect(Collectors.toList())
        );
    }
}
