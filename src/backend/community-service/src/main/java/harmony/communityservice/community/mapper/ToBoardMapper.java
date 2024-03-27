package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterBoardRequest;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.domain.UserRead;

public class ToBoardMapper {

    public static Board convert(RegisterBoardRequest registerBoardRequest, UserRead userRead, Channel channel) {
        return Board.builder()
                .title(registerBoardRequest.title())
                .content(registerBoardRequest.content())
                .channel(channel)
                .userId(userRead.getUserId())
                .writerName(userRead.getNickname())
                .writerProfile(userRead.getProfile())
                .build();
    }
}
