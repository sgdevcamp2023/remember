package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterBoardRequest;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.UserRead;

public class ToBoardMapper {

    public static Board convert(RegisterBoardRequest registerBoardRequest, UserRead userRead) {
        return Board.builder()
                .title(registerBoardRequest.title())
                .content(registerBoardRequest.content())
                .channelId(registerBoardRequest.channelId())
                .writerId(userRead.getUserId())
                .writerName(userRead.getUserInfo().getNickname())
                .writerProfile(userRead.getUserInfo().getProfile())
                .build();
    }
}
