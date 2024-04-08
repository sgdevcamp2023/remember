package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardResponse;

public class ToSearchBoardsResponseMapper {
    public static SearchBoardResponse convert(Board targetBoard,Long commentCount) {
        return SearchBoardResponse.builder()
                .boardId(targetBoard.getBoardId())
                .commentCount(commentCount)
                .title(targetBoard.getContent().getTitle())
                .content(targetBoard.getContent().getContent())
                .userId(targetBoard.getWriterInfo().getWriterId())
                .writer(targetBoard.getWriterInfo().getCommonUserInfo().getNickname())
                .createdAt(targetBoard.getCreationTime().getCreatedAt())
                .channelId(targetBoard.getChannelId())
                .modified(targetBoard.getModifiedInfo().getModifiedType())
                .searchEmojiResponses(targetBoard.makeSearchEmojisResponse().getSearchEmojiResponses())
                .build();
    }
}
