package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.repository.GuildUserCommandRepository;
import harmony.communityservice.community.command.service.GuildUserCommandService;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.domain.GuildUser;
import harmony.communityservice.community.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildUserCommandServiceImpl implements GuildUserCommandService {

    private final GuildUserCommandRepository guildUserCommandRepository;
    @Override
    public void register(Guild guild, User user) {
        GuildUser guildUser = GuildUser.make(user,guild);
        guildUserCommandRepository.save(guildUser);
    }
}
