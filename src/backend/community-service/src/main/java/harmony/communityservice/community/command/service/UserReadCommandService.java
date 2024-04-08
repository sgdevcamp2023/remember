package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.RegisterUserReadRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
public interface UserReadCommandService {

    void register(@Validated RegisterUserReadRequest registerUserReadRequest);
}
