package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.RegisterUserReadRequest;
import harmony.communityservice.community.command.repository.UserReadCommandRepository;
import harmony.communityservice.community.command.service.UserReadCommandService;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.mapper.ToUserReadMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadCommandServiceImpl implements UserReadCommandService {

    private final UserReadCommandRepository userReadCommandRepository;

    @Override
    public void register(RegisterUserReadRequest registerUserReadRequest) {
        UserRead userRead = ToUserReadMapper.convert(registerUserReadRequest);
        userReadCommandRepository.save(userRead);
    }
}
