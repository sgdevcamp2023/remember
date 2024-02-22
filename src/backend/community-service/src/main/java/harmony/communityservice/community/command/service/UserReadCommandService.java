package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.UserReadRequestDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Transactional
public interface UserReadCommandService {

    void save(@Validated UserReadRequestDto requestDto);
}
