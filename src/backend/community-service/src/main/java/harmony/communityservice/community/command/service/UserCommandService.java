package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.UserStoreRequestDto;

public interface UserCommandService {
    void save(UserStoreRequestDto requestDto);
}
