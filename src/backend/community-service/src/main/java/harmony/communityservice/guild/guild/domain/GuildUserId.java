package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class GuildUserId extends LongTypeIdentifier {
    public GuildUserId(Long id) {
        super(id);
    }

    public static GuildUserId make(long id) {
        return new GuildUserId(id);
    }

    public static class GuildUserIdJavaType extends LongTypeIdentifierJavaType<GuildUserId> {

        public GuildUserIdJavaType() {
            super(GuildUserId.class);
        }
    }
}
