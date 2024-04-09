package harmony.communityservice.room.dto;

import harmony.communityservice.user.domain.User;

public class ToUserIdsMapper {
    public static Long convert(User user) {
        return user.getUserId();
    }
}
