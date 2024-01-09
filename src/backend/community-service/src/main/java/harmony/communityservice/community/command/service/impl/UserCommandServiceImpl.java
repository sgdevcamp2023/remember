package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.domain.User;
import harmony.communityservice.community.command.dto.UserStoreRequestDto;
import harmony.communityservice.community.command.repository.UserCommandRepository;
import harmony.communityservice.community.command.service.UserCommandService;
import harmony.communityservice.community.mapper.ToUserMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserCommandRepository userCommandRepository;

    @Override
    public void save(UserStoreRequestDto requestDto) {
        User user = ToUserMapper.convert(requestDto);
        userCommandRepository.save(user);
    }
}
