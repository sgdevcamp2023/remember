package harmony.communityservice.room.mapper;

import harmony.communityservice.user.adapter.out.persistence.UserJpaEntity;
import harmony.communityservice.user.adapter.out.persistence.UserId;

public class ToUserIdsMapper {
    public static UserId convert(UserJpaEntity user) {
        return user.getUserId();
    }
}
