package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.domainentity.LongTypeIdentifier;
import harmony.communityservice.common.domainentity.LongTypeIdentifierJavaType;

public class GuildReadIdJpaVO extends LongTypeIdentifier {

    public GuildReadIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static GuildReadIdJpaVO make(long id) {
        return new GuildReadIdJpaVO(id);
    }

    public static class GuildReadIdJavaType extends LongTypeIdentifierJavaType<GuildReadIdJpaVO> {

        public GuildReadIdJavaType() {
            super(GuildReadIdJpaVO.class);
        }
    }
}
