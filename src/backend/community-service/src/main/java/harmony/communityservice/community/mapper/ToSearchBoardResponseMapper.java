package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardDetailResponse;
import harmony.communityservice.community.query.dto.SearchCommentsResponse;
import harmony.communityservice.community.query.dto.SearchEmojisResponse;
import harmony.communityservice.community.query.dto.SearchImagesResponse;

public class ToSearchBoardResponseMapper {

    public static SearchBoardDetailResponse convert(Board board, SearchCommentsResponse commentsResponseDto,
                                                    SearchEmojisResponse emojisResponseDto, SearchImagesResponse imagesResponseDto, long boardId) {
        return SearchBoardDetailResponse.builder()
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
