package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

class GuildIdJpaVO extends LongTypeIdentifier {

    public GuildIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static GuildIdJpaVO make(long id) {
        return new GuildIdJpaVO(id);
    }

    public static class GuildIdJavaType extends LongTypeIdentifierJavaType<GuildIdJpaVO> {
        public GuildIdJavaType() {
            super(GuildIdJpaVO.class);
        }
    }
}
