package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterBoardRequest;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.domain.UserRead;

public class ToBoardMapper {

    public static Board convert(RegisterBoardRequest requestDto, UserRead userRead, Channel channel) {
        return Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .channel(channel)
                .userId(userRead.getUserId())
                .writerName(userRead.getNickname())
                .writerProfile(userRead.getProfile())
                .build();
    }
}
