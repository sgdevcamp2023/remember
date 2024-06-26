package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO.BoardIdJavaType;
import harmony.communityservice.common.domainentity.AggregateRootEntity;
import harmony.communityservice.common.generic.WriterInfoJpaVO;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO.ChannelIdJavaType;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board", indexes = @Index(name = "idx__channelId", columnList = "channel_id"))
public class BoardEntity extends AggregateRootEntity<BoardEntity, BoardIdJpaVO> {

    @Id
    @Column(name = "board_id")
    @JavaType(BoardIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BoardIdJpaVO boardId;

    @Column(name = "channel_id")
    @JavaType(ChannelIdJavaType.class)
    private ChannelIdJpaVO channelId;

    @Embedded
    private ContentJpaVO content;

    @Embedded
    private WriterInfoJpaVO writerInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private final List<ImageEntity> images = new ArrayList<>();

    @Version
    private Long version;


    @Builder
    public BoardEntity(ChannelIdJpaVO channelId, List<ImageEntity> images,
                       String title, String content, String writerName, Long writerId, String writerProfile) {
        this.channelId = channelId;
        this.images.addAll(images);
        this.content = makeContent(title, content);
        this.writerInfo = makeWriterInfo(writerName, writerId, writerProfile);
    }

    private ContentJpaVO makeContent(String title, String content) {
        return ContentJpaVO.make(title, content);
    }

    private WriterInfoJpaVO makeWriterInfo(String writerName, Long writerId, String writerProfile) {
        return WriterInfoJpaVO.make(writerName, writerId, writerProfile);
    }

    @Override
    public BoardIdJpaVO getId() {
        return boardId;
    }
}
