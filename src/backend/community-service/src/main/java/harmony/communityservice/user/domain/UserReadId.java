package harmony.communityservice.user.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;


public class UserReadId extends LongTypeIdentifier {

    public UserReadId(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static UserReadId make(long id) {
        return new UserReadId(id);
    }

    public static class UserReadIdJavaType extends LongTypeIdentifierJavaType<UserReadId> {
        public UserReadIdJavaType() {
            super(UserReadId.class);
        }
    }
}
