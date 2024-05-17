package harmony.communityservice.guild.guild.application.port.in;

import org.springframework.web.multipart.MultipartFile;

public record RegisterGuildCommand(MultipartFile file, Long managerId, String guildName) {
}
