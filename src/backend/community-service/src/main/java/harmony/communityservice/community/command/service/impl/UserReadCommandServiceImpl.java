package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.RegisterUserReadRequest;
import harmony.communityservice.community.command.repository.UserReadCommandRepository;
import harmony.communityservice.community.command.service.UserReadCommandService;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.mapper.ToUserReadMapper;
import harmony.communityservice.community.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class UserReadCommandServiceImpl implements UserReadCommandService {

    private final UserReadCommandRepository userReadCommandRepository;
    private final UserQueryService userQueryService;

    @Override
    public void register(RegisterUserReadRequest registerUserReadRequest) {
        User targetUser = userQueryService.searchByUserId(registerUserReadRequest.userId());
        UserRead userRead = ToUserReadMapper.convert(registerUserReadRequest, targetUser);
        userReadCommandRepository.save(userRead);
    }
}
