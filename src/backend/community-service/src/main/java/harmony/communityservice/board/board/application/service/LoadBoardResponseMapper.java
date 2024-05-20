package harmony.communityservice.board.board.application.service;

import harmony.communityservice.board.board.application.port.in.LoadBoardResponse;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisResponse;
import java.time.ZoneId;

class LoadBoardResponseMapper {
    static LoadBoardResponse convert(Board targetBoard, Long commentCount,
                                            LoadEmojisResponse searchEmojisResponse) {
        return LoadBoardResponse.builder()
                .boardId(targetBoard.getBoardId().getId())
                .commentCount(commentCount)
                .title(targetBoard.getContent().getTitle())
                .content(targetBoard.getContent().getContent())
                .userId(targetBoard.getWriterInfo().getWriterId())
                .writer(targetBoard.getWriterInfo().getCommonUserInfo().getNickname())
                .createdAt(targetBoard.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .channelId(targetBoard.getChannelId().getId())
                .modified(targetBoard.getType())
                .searchEmojiResponses(searchEmojisResponse)
                .build();
    }
}
