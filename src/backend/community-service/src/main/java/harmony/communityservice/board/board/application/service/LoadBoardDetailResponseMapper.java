package harmony.communityservice.board.board.application.service;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.application.port.in.LoadBoardDetailResponse;
import harmony.communityservice.board.board.application.port.in.LoadImageResponse;
import harmony.communityservice.board.board.application.port.in.LoadImagesResponse;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsResponse;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisResponse;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

class LoadBoardDetailResponseMapper {
    static LoadBoardDetailResponse convert(Board board, LoadCommentsResponse loadCommentsResponse,
                                                  LoadEmojisResponse loadEmojisResponse) {

        List<LoadImageResponse> searchImageResponses = board.getImages().stream()
                .map(image -> new LoadImageResponse(image.getImageUrl()))
                .collect(Collectors.toList());
        return LoadBoardDetailResponse.builder()
                .title(board.getContent().getTitle())
                .content(board.getContent().getContent())
                .loadCommentsResponse(loadCommentsResponse)
                .loadEmojisResponse(loadEmojisResponse)
                .boardId(board.getBoardId().getId())
                .loadImagesResponse(new LoadImagesResponse(searchImageResponses))
                .modified(board.getType())
                .createdAt(board.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .userId(board.getWriterInfo().getWriterId())
                .writerName(board.getWriterInfo().getCommonUserInfo().getNickname())
                .build();
    }
}
