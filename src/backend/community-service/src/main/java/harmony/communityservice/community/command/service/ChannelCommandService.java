package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.ChannelRegistrationRequestDto;

public interface ChannelCommandService {

    void registration(ChannelRegistrationRequestDto requestDto);
}
