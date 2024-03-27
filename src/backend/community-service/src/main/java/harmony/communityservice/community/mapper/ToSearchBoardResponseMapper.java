package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardDetailResponse;
import harmony.communityservice.community.query.dto.SearchCommentsResponse;
import harmony.communityservice.community.query.dto.SearchEmojisResponse;
import harmony.communityservice.community.query.dto.SearchImagesResponse;

public class ToSearchBoardResponseMapper {

    public static SearchBoardDetailResponse convert(Board board, SearchCommentsResponse searchCommentsResponse,
                                                    SearchEmojisResponse searchEmojisResponse, SearchImagesResponse searchImagesResponse, long boardId) {
        return SearchBoardDetailResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .commentsResponseDto(searchCommentsResponse)
                .emojisResponseDto(searchEmojisResponse)
                .boardId(boardId)
                .imagesResponseDto(searchImagesResponse)
                .modified(board.isModified())
                .createdAt(board.getCreatedAt())
                .userId(board.getUserId())
                .writerName(board.getWriterName())
                .build();
    }
}
