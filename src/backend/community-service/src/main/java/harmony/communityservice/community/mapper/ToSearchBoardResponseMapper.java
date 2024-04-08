package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardDetailResponse;
import harmony.communityservice.community.query.dto.SearchCommentsResponse;
import harmony.communityservice.community.query.dto.SearchEmojisResponse;

public class ToSearchBoardResponseMapper {
    public static SearchBoardDetailResponse convert(Board board, SearchCommentsResponse searchCommentsResponse,
                                                    SearchEmojisResponse searchEmojisResponse) {
        return SearchBoardDetailResponse.builder()
                .title(board.getContent().getTitle())
                .content(board.getContent().getContent())
                .searchCommentsResponse(searchCommentsResponse)
                .searchEmojisResponse(searchEmojisResponse)
                .boardId(board.getBoardId())
                .searchImagesResponse(board.makeSearchImagesResponse())
                .modified(board.getModifiedInfo().getModifiedType())
                .createdAt(board.getCreationTime().getCreatedAt())
                .userId(board.getWriterInfo().getWriterId())
                .writerName(board.getWriterInfo().getCommonUserInfo().getNickname())
                .build();
    }
}
