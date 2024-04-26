package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class GuildId extends LongTypeIdentifier {

    public GuildId(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static GuildId make(long id) {
        return new GuildId(id);
    }

    public static class GuildIdJavaType extends LongTypeIdentifierJavaType<GuildId> {
        public GuildIdJavaType() {
            super(GuildId.class);
        }
    }
}
