package harmony.communityservice.guild.category.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class CategoryIdJpaVO extends EntityLongTypeIdentifier {

    public CategoryIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static CategoryIdJpaVO make(long id) {
        return new CategoryIdJpaVO(id);
    }

    public static class CategoryIdJavaType extends EntityLongTypeIdentifierJavaType<CategoryIdJpaVO> {
        public CategoryIdJavaType() {
            super(CategoryIdJpaVO.class);
        }
    }
}
