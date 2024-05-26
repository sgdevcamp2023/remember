package harmony.communityservice.board.board.domain;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.time.Instant;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Board extends Domain<Board, BoardId> {

    private final BoardId boardId;

    private final ChannelId channelId;

    private final Content content;

    private final WriterInfo writerInfo;

    private final List<Image> images;

    private final Instant createdAt;

    private final ModifiedType type;

    @Builder
    public Board(BoardId boardId, ChannelId channelId, String title, String content, List<Image> images, Long writerId,
                 String username, String profile, Instant createdAt, ModifiedType type) {
        verifyBoardId(boardId);
        this.boardId = boardId;
        verifyChannelId(channelId);
        this.channelId = channelId;
        this.content = makeContent(title, content);
        this.images = images;
        this.writerInfo = makeWriterInfo(writerId, username, profile);
        this.createdAt = createdAt;
        this.type = type;
    }

    private void verifyBoardId(BoardId boardId) {
        if (boardId != null && boardId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("boardId의 범위가 1 미만입니다");
        }
    }

    private void verifyChannelId(ChannelId channelId) {
        if (channelId == null) {
            throw new NotFoundDataException("channelId가 존재하지 않습니다");
        }

        if (channelId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("channelId의 범위가 1 미만입니다");
        }
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

    @Override
    public BoardId getId() {
        return boardId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BoardId extends ValueObject<BoardId> {
        private final Long id;

        public static BoardId make(Long boardId) {
            return new BoardId(boardId);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}
