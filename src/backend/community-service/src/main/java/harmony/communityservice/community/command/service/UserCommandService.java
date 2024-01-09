package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.UserStoreRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserCommandService {
    void save(UserStoreRequestDto requestDto);
}
