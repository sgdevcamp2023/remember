package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.BoardsResponseDto;
import harmony.communityservice.community.query.dto.EmojiResponseDto;
import java.util.List;

public class ToBoardsResponseDtoMapper {

    public static BoardsResponseDto convert(Board findBoard, List<EmojiResponseDto> emojiResponseDtos) {
        return BoardsResponseDto.builder()
                .boardId(findBoard.getBoardId())
                .commentCount(findBoard.commentCount())
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
