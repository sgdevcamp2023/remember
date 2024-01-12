package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.UserNicknameUpdateRequestDto;
import harmony.communityservice.community.command.dto.UserProfileUpdateRequestDto;
import harmony.communityservice.community.command.dto.UserStoreRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserCommandService {
    void save(UserStoreRequestDto requestDto);

    void updateProfile(UserProfileUpdateRequestDto requestDto);

    void updateNickname(UserNicknameUpdateRequestDto requestDto);
}
