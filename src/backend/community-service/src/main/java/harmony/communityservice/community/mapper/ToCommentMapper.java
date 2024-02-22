package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.CommentRegistrationRequestDto;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Comment;

public class ToCommentMapper {

    public static Comment convert(CommentRegistrationRequestDto requestDto, Board findBoard) {
        return Comment.builder()
                .comment(requestDto.getComment())
                .board(findBoard)
                .userId(requestDto.getUserId())
                .writerName(requestDto.getWriterName())
                .writerProfile(requestDto.getWriterProfile())
                .build();
    }
}
