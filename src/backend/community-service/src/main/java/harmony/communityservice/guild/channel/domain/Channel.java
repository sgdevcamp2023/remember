package harmony.communityservice.guild.channel.domain;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Channel {

    private ChannelId channelId;

    private GuildId guildId;




    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ChannelId{
        private Long id;

        public static ChannelId make(Long channelId) {
            return new ChannelId(channelId);
        }
    }
}
