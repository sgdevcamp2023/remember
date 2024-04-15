package harmony.communityservice.room.mapper;

import harmony.communityservice.user.domain.User;

public class ToUserIdsMapper {
    public static Long convert(User user) {
        return user.getUserId();
    }
}
