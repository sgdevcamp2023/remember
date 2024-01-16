package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.BoardResponseDto;
import harmony.communityservice.community.query.dto.EmojiResponseDto;
import java.util.List;

public class ToBoardResponseDtoMapper {

    public static BoardResponseDto convert(Board findBoard, List<EmojiResponseDto> emojiResponseDtos) {
        return BoardResponseDto.builder()
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
