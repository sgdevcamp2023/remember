package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardResponse;

public class ToSearchBoardsResponseMapper {
    public static SearchBoardResponse convert(Board targetBoard) {
        return SearchBoardResponse.builder()
                .boardId(targetBoard.getBoardId())
                .commentCount(targetBoard.countComments())
                .title(targetBoard.getTitle())
                .content(targetBoard.getContent())
                .userId(targetBoard.getUserId())
                .writer(targetBoard.getWriterName())
                .createdAt(targetBoard.getCreatedAt())
                .channelId(targetBoard.getChannel().getChannelId())
                .modified(targetBoard.isModified())
                .searchEmojiResponses(targetBoard.makeSearchEmojisResponse().getSearchEmojiResponses())
                .build();
    }
}
