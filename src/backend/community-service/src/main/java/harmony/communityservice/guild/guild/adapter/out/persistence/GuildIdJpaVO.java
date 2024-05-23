package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class GuildIdJpaVO extends EntityLongTypeIdentifier {

    public GuildIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static GuildIdJpaVO make(long id) {
        return new GuildIdJpaVO(id);
    }

    public static class GuildIdJavaType extends EntityLongTypeIdentifierJavaType<GuildIdJpaVO> {
        public GuildIdJavaType() {
            super(GuildIdJpaVO.class);
        }
    }
}
