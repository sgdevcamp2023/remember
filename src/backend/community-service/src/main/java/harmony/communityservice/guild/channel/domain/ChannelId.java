package harmony.communityservice.guild.channel.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class ChannelId extends LongTypeIdentifier {

    public ChannelId(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static ChannelId make(Long id) {
        return new ChannelId(id);
    }

    public static class ChannelIdJavaType extends LongTypeIdentifierJavaType<ChannelId> {
        public ChannelIdJavaType() {
            super(ChannelId.class);
        }
    }
}
