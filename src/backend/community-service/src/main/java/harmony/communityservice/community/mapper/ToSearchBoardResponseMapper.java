package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardDetailResponse;

public class ToSearchBoardResponseMapper {
    public static SearchBoardDetailResponse convert(Board board) {
        return SearchBoardDetailResponse.builder()
                .title(board.getContent().getTitle())
                .content(board.getContent().getContent())
                .searchCommentsResponse(board.makeSearchCommentsResponse())
                .searchEmojisResponse(board.makeSearchEmojisResponse())
                .boardId(board.getBoardId())
                .searchImagesResponse(board.makeSearchImagesResponse())
                .modified(board.getModifiedInfo().isModified())
                .createdAt(board.getCreationTime().getCreatedAt())
                .userId(board.getWriterInfo().getWriterId())
                .writerName(board.getWriterInfo().getWriterName())
                .build();
    }
}
