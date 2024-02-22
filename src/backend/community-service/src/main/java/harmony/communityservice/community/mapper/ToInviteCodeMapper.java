package harmony.communityservice.community.mapper;

public class ToInviteCodeMapper {

    public static String convert(String code, Long userId, Long guildId) {
        return code + "." + userId + "." + guildId;
    }
}
