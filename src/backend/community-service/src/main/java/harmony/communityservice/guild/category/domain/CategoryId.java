package harmony.communityservice.guild.category.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class CategoryId extends LongTypeIdentifier {

    public CategoryId(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static CategoryId make(long id) {
        return new CategoryId(id);
    }

    public static class CategoryIdJavaType extends LongTypeIdentifierJavaType<CategoryId> {
        public CategoryIdJavaType() {
            super(CategoryId.class);
        }
    }
}
