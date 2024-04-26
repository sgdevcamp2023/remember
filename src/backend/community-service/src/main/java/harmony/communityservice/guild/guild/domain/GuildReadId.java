package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class GuildReadId extends LongTypeIdentifier {

    public GuildReadId(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static GuildReadId make(long id) {
        return new GuildReadId(id);
    }

    public static class GuildReadIdJavaType extends LongTypeIdentifierJavaType<GuildReadId> {

        public GuildReadIdJavaType() {
            super(GuildReadId.class);
        }
    }
}
