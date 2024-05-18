package harmony.communityservice.board.board.application.port.in;

import lombok.Builder;

@Builder(toBuilder = true)
public record ModifyBoardCommand(Long userId, Long boardId, String title, String content) {
}
