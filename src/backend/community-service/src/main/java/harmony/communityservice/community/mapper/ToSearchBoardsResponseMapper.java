package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardResponse;
import harmony.communityservice.community.query.dto.SearchEmojiResponse;
import java.util.List;

public class ToSearchBoardsResponseMapper {

    public static SearchBoardResponse convert(Board findBoard, List<SearchEmojiResponse> emojiResponseDtos) {
        return SearchBoardResponse.builder()
                .boardId(findBoard.getBoardId())
                .commentCount(findBoard.countComments())
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .userId(findBoard.getUserId())
                .writer(findBoard.getWriterName())
                .createdAt(findBoard.getCreatedAt())
                .channelId(findBoard.getChannel().getChannelId())
                .modified(findBoard.isModified())
                .emojiResponseDtos(emojiResponseDtos)
                .build();
    }
}
