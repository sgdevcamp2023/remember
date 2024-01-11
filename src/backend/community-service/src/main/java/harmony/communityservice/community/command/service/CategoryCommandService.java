package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.CategoryRegistrationRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CategoryCommandService {
    void save(CategoryRegistrationRequestDto requestDto);
}
