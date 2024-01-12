package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.ChannelCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaChannelCommandRepository;
import harmony.communityservice.community.domain.Channel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelCommandRepositoryImpl implements ChannelCommandRepository {

    private final JpaChannelCommandRepository jpaChannelCommandRepository;

    @Override
    public void save(Channel channel) {
        jpaChannelCommandRepository.save(channel);
    }
}
