package harmony.communityservice.board.board.domain;

import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Board {

    private final BoardId boardId;

    private final ChannelId channelId;

    private final Content content;

    private final WriterInfo writerInfo;

    private final List<Image> images;

    @Builder
    public Board(BoardId boardId, ChannelId channelId, String title, String content, List<Image> images, Long writerId,
                 String username, String profile) {
        this.boardId = boardId;
        this.channelId = channelId;
        this.content = makeContent(title, content);
        this.images = images;
        this.writerInfo = makeWriterInfo(writerId, username, profile);
    }

    private Content makeContent(String title, String content) {
        return Content.builder()
                .content(content)
                .title(title)
                .build();
    }


    private WriterInfo makeWriterInfo(Long writerId, String username, String profile) {
        return WriterInfo.builder()
                .userName(username)
                .profile(profile)
                .writerId(writerId)
                .build();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BoardId {
        private final Long id;

        public static BoardId make(Long boardId) {
            return new BoardId(boardId);
        }
    }
}
