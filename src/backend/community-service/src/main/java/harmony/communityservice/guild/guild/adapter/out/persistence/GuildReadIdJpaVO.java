package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class GuildReadIdJpaVO extends EntityLongTypeIdentifier {

    public GuildReadIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static class GuildReadIdJavaType extends EntityLongTypeIdentifierJavaType<GuildReadIdJpaVO> {
        public GuildReadIdJavaType() {
            super(GuildReadIdJpaVO.class);
        }
    }
}
