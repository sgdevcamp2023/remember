package harmony.communityservice.guild.guild.adapter.in.web;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchGuildReadResponse(Long guildReadId, Long guildId, Long userId, String name, String profile) {
}
