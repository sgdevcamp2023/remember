package harmony.communityservice.community.mapper;

public class ToInvitationCodeMapper {

    public static String convert(String code, Long userId, Long guildId) {
        return code + "." + userId + "." + guildId;
    }
}
