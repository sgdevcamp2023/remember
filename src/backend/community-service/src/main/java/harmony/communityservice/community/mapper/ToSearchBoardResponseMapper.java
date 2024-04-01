package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardDetailResponse;

public class ToSearchBoardResponseMapper {
    public static SearchBoardDetailResponse convert(Board board) {
        return SearchBoardDetailResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .searchCommentsResponse(board.makeSearchCommentsResponse())
                .searchEmojisResponse(board.makeSearchEmojisResponse())
                .boardId(board.getBoardId())
                .searchImagesResponse(board.makeSearchImagesResponse())
                .modified(board.isModified())
                .createdAt(board.getCreatedAt())
                .userId(board.getUserId())
                .writerName(board.getWriterName())
                .build();
    }
}
