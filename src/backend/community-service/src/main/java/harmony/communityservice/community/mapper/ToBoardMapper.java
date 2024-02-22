package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.BoardRegistrationRequestDto;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.domain.UserRead;

public class ToBoardMapper {

    public static Board convert(BoardRegistrationRequestDto requestDto, UserRead userRead, Channel channel) {
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
