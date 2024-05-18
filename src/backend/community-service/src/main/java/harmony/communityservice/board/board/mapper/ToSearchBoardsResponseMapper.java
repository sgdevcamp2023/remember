package harmony.communityservice.board.board.mapper;

import harmony.communityservice.board.board.adapter.out.persistence.BoardEntity;
import harmony.communityservice.board.board.dto.SearchBoardResponse;
import harmony.communityservice.board.emoji.dto.SearchEmojisResponse;
import java.time.ZoneId;

public class ToSearchBoardsResponseMapper {
    public static SearchBoardResponse convert(BoardEntity targetBoard, Long commentCount,
                                              SearchEmojisResponse searchEmojisResponse) {
        return SearchBoardResponse.builder()
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
