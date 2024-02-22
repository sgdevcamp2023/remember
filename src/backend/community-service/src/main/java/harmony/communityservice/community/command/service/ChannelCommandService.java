package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.ChannelDeleteRequestDto;
import harmony.communityservice.community.command.dto.ChannelRegistrationRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChannelCommandService {

    Long registration(ChannelRegistrationRequestDto requestDto);

    void remove(ChannelDeleteRequestDto requestDto);
}
