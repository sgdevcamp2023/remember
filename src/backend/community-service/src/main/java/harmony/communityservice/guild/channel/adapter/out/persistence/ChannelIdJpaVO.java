package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class ChannelIdJpaVO extends EntityLongTypeIdentifier {

    public ChannelIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static ChannelIdJpaVO make(Long id) {
        return new ChannelIdJpaVO(id);
    }

    public static class ChannelIdJavaType extends EntityLongTypeIdentifierJavaType<ChannelIdJpaVO> {
        public ChannelIdJavaType() {
            super(ChannelIdJpaVO.class);
        }
    }
}
