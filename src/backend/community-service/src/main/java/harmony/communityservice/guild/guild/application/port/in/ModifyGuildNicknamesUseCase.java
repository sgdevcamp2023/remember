package harmony.communityservice.guild.guild.application.port.in;

public interface ModifyGuildNicknamesUseCase {

    void modify(Long userId, String nickname);
}
