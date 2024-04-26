package harmony.communityservice.board.board.mapper;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.dto.SearchBoardResponse;
import harmony.communityservice.board.emoji.dto.SearchEmojisResponse;

public class ToSearchBoardsResponseMapper {
    public static SearchBoardResponse convert(Board targetBoard, Long commentCount,
                                              SearchEmojisResponse searchEmojisResponse) {
        return SearchBoardResponse.builder()
                .boardId(targetBoard.getBoardId().getId())
                .commentCount(commentCount)
                .title(targetBoard.getContent().getTitle())
                .content(targetBoard.getContent().getContent())
                .userId(targetBoard.getWriterInfo().getWriterId())
                .writer(targetBoard.getWriterInfo().getCommonUserInfo().getNickname())
                .createdAt(targetBoard.getCreationTime().getCreatedAt())
                .channelId(targetBoard.getChannelId().getId())
                .modified(targetBoard.getModifiedInfo().getModifiedType())
                .searchEmojiResponses(searchEmojisResponse)
                .build();
    }
}
