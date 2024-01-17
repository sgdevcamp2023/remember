package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.CommentRegistrationRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentCommandService {
    void save(CommentRegistrationRequestDto requestDto);
}
