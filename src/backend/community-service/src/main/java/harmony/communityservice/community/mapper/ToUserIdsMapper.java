package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.User;

public class ToUserIdsMapper {
    public static Long convert(User user) {
        return user.getUserId();
    }
}
