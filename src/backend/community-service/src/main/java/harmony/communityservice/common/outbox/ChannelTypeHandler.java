package harmony.communityservice.common.outbox;


import harmony.communityservice.guild.channel.domain.ChannelType;

public class ChannelTypeHandler extends EnumTypeHandler<ChannelType> {
    public ChannelTypeHandler() {
        super(ChannelType.class);
    }
}
