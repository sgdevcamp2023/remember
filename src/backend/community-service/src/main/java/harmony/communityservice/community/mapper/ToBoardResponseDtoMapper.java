package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.BoardResponseDto;
import harmony.communityservice.community.query.dto.CommentsResponseDto;
import harmony.communityservice.community.query.dto.EmojisResponseDto;
import harmony.communityservice.community.query.dto.ImagesResponseDto;

public class ToBoardResponseDtoMapper {

    public static BoardResponseDto convert(Board board, CommentsResponseDto commentsResponseDto,
                                           EmojisResponseDto emojisResponseDto, ImagesResponseDto imagesResponseDto, long boardId) {
        return BoardResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .commentsResponseDto(commentsResponseDto)
                .emojisResponseDto(emojisResponseDto)
                .boardId(boardId)
                .imagesResponseDto(imagesResponseDto)
                .modified(board.isModified())
                .createdAt(board.getCreatedAt())
                .userId(board.getUserId())
                .writerName(board.getWriterName())
                .build();
    }
}
