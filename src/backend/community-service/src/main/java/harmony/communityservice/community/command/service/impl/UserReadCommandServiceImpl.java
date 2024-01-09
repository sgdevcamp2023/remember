package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.UserReadRequestDto;
import harmony.communityservice.community.command.repository.UserReadCommandRepository;
import harmony.communityservice.community.command.service.UserReadCommandService;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.mapper.ToUserReadMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadCommandServiceImpl implements UserReadCommandService {

    private final UserReadCommandRepository userReadCommandRepository;

    @Override
    public void save(UserReadRequestDto requestDto) {
        UserRead userRead = ToUserReadMapper.convert(requestDto);
        userReadCommandRepository.save(userRead);
    }
}
