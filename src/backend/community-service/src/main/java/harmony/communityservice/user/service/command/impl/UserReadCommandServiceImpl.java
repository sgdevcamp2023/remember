package harmony.communityservice.user.service.command.impl;

import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.user.dto.RegisterUserReadRequest;
import harmony.communityservice.user.mapper.ToUserReadMapper;
import harmony.communityservice.user.repository.command.UserReadCommandRepository;
import harmony.communityservice.user.service.command.UserReadCommandService;
import harmony.communityservice.user.service.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class UserReadCommandServiceImpl implements UserReadCommandService {

    private final UserQueryService userQueryService;
    private final UserReadCommandRepository userReadCommandRepository;


    @Override
    public void register(RegisterUserReadRequest registerUserReadRequest) {
        User targetUser = userQueryService.searchByUserId(registerUserReadRequest.userId());
        UserRead userRead = ToUserReadMapper.convert(registerUserReadRequest, targetUser);
        userReadCommandRepository.save(userRead);
    }
}
