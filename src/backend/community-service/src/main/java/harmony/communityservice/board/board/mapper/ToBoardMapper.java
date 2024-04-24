package harmony.communityservice.board.board.mapper;

import harmony.communityservice.board.board.dto.RegisterBoardRequest;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.user.domain.UserRead;
import java.util.List;

public class ToBoardMapper {

    public static Board convert(RegisterBoardRequest registerBoardRequest, UserRead userRead, List<Image> images) {
        return Board.builder()
                .title(registerBoardRequest.title())
                .content(registerBoardRequest.content())
                .channelId(registerBoardRequest.channelId())
                .writerId(userRead.getUserId())
                .writerName(userRead.getUserInfo().getNickname())
                .writerProfile(userRead.getUserInfo().getProfile())
                .images(images)
                .build();
    }
}
