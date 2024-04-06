package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardResponse;

public class ToSearchBoardsResponseMapper {
    public static SearchBoardResponse convert(Board targetBoard) {
        return SearchBoardResponse.builder()
                .boardId(targetBoard.getBoardId())
                .commentCount(targetBoard.countComments())
                .title(targetBoard.getContent().getTitle())
                .content(targetBoard.getContent().getContent())
                .userId(targetBoard.getWriterInfo().getWriterId())
                .writer(targetBoard.getWriterInfo().getWriterName())
                .createdAt(targetBoard.getCreationTime().getCreatedAt())
                .channelId(targetBoard.getChannel().getChannelId())
                .modified(targetBoard.getModifiedInfo().isModified())
                .searchEmojiResponses(targetBoard.makeSearchEmojisResponse().getSearchEmojiResponses())
                .build();
    }
}
