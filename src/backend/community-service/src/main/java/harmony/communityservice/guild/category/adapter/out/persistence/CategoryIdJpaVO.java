package harmony.communityservice.guild.category.adapter.out.persistence;

import harmony.communityservice.common.domainentity.LongTypeIdentifier;
import harmony.communityservice.common.domainentity.LongTypeIdentifierJavaType;

public class CategoryIdJpaVO extends LongTypeIdentifier {

    public CategoryIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static CategoryIdJpaVO make(long id) {
        return new CategoryIdJpaVO(id);
    }

    public static class CategoryIdJavaType extends LongTypeIdentifierJavaType<CategoryIdJpaVO> {
        public CategoryIdJavaType() {
            super(CategoryIdJpaVO.class);
        }
    }
}
