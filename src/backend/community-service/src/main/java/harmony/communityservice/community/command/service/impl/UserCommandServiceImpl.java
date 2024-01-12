package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.UserProfileUpdateRequestDto;
import harmony.communityservice.community.command.dto.UserStoreRequestDto;
import harmony.communityservice.community.command.repository.UserCommandRepository;
import harmony.communityservice.community.command.service.UserCommandService;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.mapper.ToUserMapper;
import harmony.communityservice.community.query.service.UserQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserCommandRepository userCommandRepository;
    private final UserQueryService userQueryService;
    private final UserReadQueryService userReadQueryService;

    @Override
    public void save(UserStoreRequestDto requestDto) {
        User user = ToUserMapper.convert(requestDto);
        userCommandRepository.save(user);
    }

    @Override
    public void updateProfile(UserProfileUpdateRequestDto requestDto) {
        User findUser = userQueryService.findUser(requestDto.getUserId());
        findUser.updateProfile(requestDto.getProfile());
        userReadQueryService.findUserReadsByUserId(requestDto.getUserId())
                .forEach(findUserRead -> findUserRead.updateProfile(requestDto.getProfile()));
    }
}
