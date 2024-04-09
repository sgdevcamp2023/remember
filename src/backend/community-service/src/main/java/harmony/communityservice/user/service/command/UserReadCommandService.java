package harmony.communityservice.user.service.command;

import harmony.communityservice.user.dto.RegisterUserReadRequest;

public interface UserReadCommandService {

    void register(RegisterUserReadRequest registerUserReadRequest);
}
