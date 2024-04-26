package harmony.communityservice.room.mapper;

import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.UserId;

public class ToUserIdsMapper {
    public static UserId convert(User user) {
        return user.getUserId();
    }
}
