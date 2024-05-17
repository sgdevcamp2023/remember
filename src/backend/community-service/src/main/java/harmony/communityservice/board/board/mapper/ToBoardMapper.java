package harmony.communityservice.board.board.mapper;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.board.board.dto.RegisterBoardRequest;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import harmony.communityservice.user.adapter.out.persistence.UserReadEntity;
import java.util.List;

public class ToBoardMapper {

    public static Board convert(RegisterBoardRequest registerBoardRequest, UserReadEntity userRead, List<Image> images) {
        return Board.builder()
                .title(registerBoardRequest.title())
                .content(registerBoardRequest.content())
                .channelId(ChannelIdJpaVO.make(registerBoardRequest.channelId()))
                .writerId(userRead.getUserId().getId())
                .writerName(userRead.getUserInfo().getNickname())
                .writerProfile(userRead.getUserInfo().getProfile())
                .images(images)
                .build();
    }
}
