package harmony.communityservice.board.board.application.service;

import harmony.communityservice.board.board.application.port.in.RegisterBoardCommand;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import java.util.List;

class BoardMapper {

    static Board convert(RegisterBoardCommand registerBoardCommand, GuildRead guildRead,
                                List<Image> images) {
        return Board.builder()
                .title(registerBoardCommand.title())
                .content(registerBoardCommand.content())
                .channelId(ChannelId.make(registerBoardCommand.channelId()))
                .writerId(guildRead.getUserId().getId())
                .username(guildRead.getCommonUserInfo().getNickname())
                .profile(guildRead.getCommonUserInfo().getProfile())
                .images(images)
                .build();
    }
}
