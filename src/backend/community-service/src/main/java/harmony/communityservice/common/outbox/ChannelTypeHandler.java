package harmony.communityservice.common.outbox;


import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelTypeJpaEnum;

public class ChannelTypeHandler extends EnumTypeHandler<ChannelTypeJpaEnum> {
    public ChannelTypeHandler() {
        super(ChannelTypeJpaEnum.class);
    }
}
