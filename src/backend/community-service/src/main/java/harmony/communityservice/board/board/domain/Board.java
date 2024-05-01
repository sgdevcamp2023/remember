package harmony.communityservice.board.board.domain;

import harmony.communityservice.board.board.domain.BoardId.BoardIdJavaType;
import harmony.communityservice.board.board.dto.ModifyBoardRequest;
import harmony.communityservice.board.board.dto.SearchImageResponse;
import harmony.communityservice.board.board.dto.SearchImagesResponse;
import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.generic.WriterInfo;
import harmony.communityservice.guild.channel.domain.ChannelId;
import harmony.communityservice.guild.channel.domain.ChannelId.ChannelIdJavaType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board", indexes = @Index(name = "idx__channelId", columnList = "channel_id"))
public class Board extends AggregateRoot<Board, BoardId> {

    @Id
    @Column(name = "board_id")
    @JavaType(BoardIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BoardId boardId;

    @Column(name = "channel_id")
    @JavaType(ChannelIdJavaType.class)
    private ChannelId channelId;

    @Embedded
    private Content content;

    @Embedded
    private WriterInfo writerInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private final List<Image> images = new ArrayList<>();

    @Version
    private Long version;


    @Builder
    public Board(ChannelId channelId, List<Image> images,
                 String title, String content, String writerName, Long writerId, String writerProfile) {
        this.channelId = channelId;
        this.images.addAll(images);
        this.content = makeContent(title, content);
        this.writerInfo = makeWriterInfo(writerName, writerId, writerProfile);
    }

    public void verifyWriter(Long writerId) {
        writerInfo.verifyWriter(writerId);
    }

    public void modifyTitleAndContent(ModifyBoardRequest modifyBoardRequest) {
        verifyWriter(modifyBoardRequest.userId());
        this.content = this.content.modify(modifyBoardRequest.title(), modifyBoardRequest.content());
        super.updateType();
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

    @Override
    public BoardId getId() {
        return boardId;
    }
}
