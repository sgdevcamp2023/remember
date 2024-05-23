package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.common.domainentity.LongTypeIdentifier;
import harmony.communityservice.common.domainentity.LongTypeIdentifierJavaType;

public class ChannelIdJpaVO extends LongTypeIdentifier {

    public ChannelIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static ChannelIdJpaVO make(Long id) {
        return new ChannelIdJpaVO(id);
    }

    public static class ChannelIdJavaType extends LongTypeIdentifierJavaType<ChannelIdJpaVO> {
        public ChannelIdJavaType() {
            super(ChannelIdJpaVO.class);
        }
    }
}
