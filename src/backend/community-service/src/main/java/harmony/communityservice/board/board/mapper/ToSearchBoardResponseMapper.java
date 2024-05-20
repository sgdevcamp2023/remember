package harmony.communityservice.board.board.mapper;

import harmony.communityservice.board.board.adapter.out.persistence.BoardEntity;
import harmony.communityservice.board.board.dto.SearchBoardDetailResponse;
import harmony.communityservice.board.comment.dto.SearchCommentsResponse;
import harmony.communityservice.board.emoji.application.port.in.SearchEmojisResponse;
import java.time.ZoneId;

public class ToSearchBoardResponseMapper {
    public static SearchBoardDetailResponse convert(BoardEntity board, SearchCommentsResponse searchCommentsResponse,
                                                    SearchEmojisResponse searchEmojisResponse) {
        return SearchBoardDetailResponse.builder()
                .title(board.getContent().getTitle())
                .content(board.getContent().getContent())
                .searchCommentsResponse(searchCommentsResponse)
                .searchEmojisResponse(searchEmojisResponse)
                .boardId(board.getBoardId().getId())
                .searchImagesResponse(board.makeSearchImagesResponse())
                .modified(board.getType())
                .createdAt(board.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .userId(board.getWriterInfo().getWriterId())
                .writerName(board.getWriterInfo().getCommonUserInfo().getNickname())
                .build();
    }
}
