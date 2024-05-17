package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

class GuildUserIdJpaVO extends LongTypeIdentifier {
    public GuildUserIdJpaVO(Long id) {
        super(id);
    }

    public static GuildUserIdJpaVO make(long id) {
        return new GuildUserIdJpaVO(id);
    }

    public static class GuildUserIdJavaType extends LongTypeIdentifierJavaType<GuildUserIdJpaVO> {

        public GuildUserIdJavaType() {
            super(GuildUserIdJpaVO.class);
        }
    }
}
