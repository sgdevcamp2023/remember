package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.feign.UserStatusClient;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.query.dto.UserStateResponseDto;
import harmony.communityservice.community.query.dto.UserStatusRequestDto;
import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadQueryServiceImpl implements UserReadQueryService {

    private final UserStatusClient userStatusClient;
    private final UserReadQueryRepository userReadQueryRepository;

    @Override
    public void existsUserIdAndGuildId(long userId, long guildId) {
        if (!userReadQueryRepository.existByUserIdAndGuildId(userId, guildId)) {
            throw new NotFoundDataException();
        }
    }

    @Override
    public UserRead findUserReadIdAndGuildId(long userId, long guildId) {
        return userReadQueryRepository.findByUserIdAndGuildId(userId, guildId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public List<UserRead> findUserReadsByUserId(long userId) {
        return userReadQueryRepository.findUserReadsByUserId(userId);
    }

    @Override
    public Map<Long, ?> findUserStatus(long guildId, long userId) {
        existsUserIdAndGuildId(guildId, userId);
        List<UserRead> findUserReads = userReadQueryRepository.findUserReadsByGuildId(guildId);

        List<UserStateResponseDto> responseDtos = findUserReads.stream()
                .map(findUserRead -> {
                    return UserStateResponseDto.builder()
                            .userName(findUserRead.getNickname())
                            .profile(findUserRead.getProfile())
                            .userId(findUserRead.getUserId())
                            .build();
                }).toList();

        List<Long> userIds = responseDtos.stream()
                .map(UserStateResponseDto::getUserId)
                .collect(Collectors.toList());

        UserStatusRequestDto userStatusRequestDto = new UserStatusRequestDto(userIds);
        Map<Long, String> userState = userStatusClient.userStatus(userStatusRequestDto);

        responseDtos.forEach(dto -> dto.updateState(userState.get(dto.getUserId())));

        return responseDtos.stream()
                .collect(Collectors.toMap(UserStateResponseDto::getUserId, dto -> dto));
    }
}