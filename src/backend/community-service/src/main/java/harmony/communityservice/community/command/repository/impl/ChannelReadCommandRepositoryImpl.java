package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.ChannelReadCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaChannelReadCommandRepository;
import harmony.communityservice.community.domain.ChannelRead;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelReadCommandRepositoryImpl implements ChannelReadCommandRepository {

    private final JpaChannelReadCommandRepository jpaChannelReadCommandRepository;

    @Override
    public void save(ChannelRead channelRead) {
        jpaChannelReadCommandRepository.save(channelRead);
    }

    @Override
    public void deleteByChannelId(Long channelId) {
        jpaChannelReadCommandRepository.deleteById(channelId);
    }
}
