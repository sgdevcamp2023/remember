package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.dto.UserStateFeignResponseDto;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.feign.UserStatusClient;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.query.dto.UserStateResponseDto;
import harmony.communityservice.community.query.dto.UserStatesResponseDto;
import harmony.communityservice.community.query.dto.UserStatusRequestDto;
import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserReadQueryServiceImpl implements UserReadQueryService {

    private final UserStatusClient userStatusClient;
    private final UserReadQueryRepository userReadQueryRepository;

    private static Map<Long, UserStateResponseDto> makeGuildStates(
            List<UserStateResponseDto> responseDtos, UserStateFeignResponseDto userState) {
        responseDtos.forEach(dto -> dto.updateState(userState.getConnectionStates().get(dto.getUserId())));
        return responseDtos.stream()
                .collect(Collectors.toMap(UserStateResponseDto::getUserId, dto -> dto));
    }

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
    public UserStatesResponseDto findUserStatus(long guildId, long userId) {
        existsUserIdAndGuildId(userId, guildId);
        List<UserStateResponseDto> responseDtos = makeUserStateResponseDtos(guildId);
        List<Long> userIds = responseDtos.stream()
                .map(UserStateResponseDto::getUserId)
                .collect(Collectors.toList());
        UserStatusRequestDto userStatusRequestDto = new UserStatusRequestDto(guildId, userIds);
        UserStateFeignResponseDto userState = userStatusClient.userStatus(userStatusRequestDto);
        log.info("{}", userState);
        Map<Long, UserStateResponseDto> guildStates = makeGuildStates(
                responseDtos, userState);
        Map<Long, Map<Long, ?>> voiceChannelStates = getVoiceChannelStates(guildId, userState);
        return new UserStatesResponseDto(guildStates, voiceChannelStates);
    }

    private List<UserStateResponseDto> makeUserStateResponseDtos(long guildId) {
        List<UserRead> findUserReads = userReadQueryRepository.findUserReadsByGuildId(guildId);
        return findUserReads.stream()
                .map(findUserRead -> {
                    return UserStateResponseDto.builder()
                            .userName(findUserRead.getNickname())
                            .profile(findUserRead.getProfile())
                            .userId(findUserRead.getUserId())
                            .build();
                }).toList();
    }

    private Map<Long, Map<Long, ?>> getVoiceChannelStates(long guildId, UserStateFeignResponseDto userState) {
        Map<Long, Set<Long>> channelStates = userState.getChannelStates();
        Map<Long, Map<Long, ?>> voiceChannelStates = new HashMap<>();
        for (Long channelId : channelStates.keySet()) {
            Set<Long> voiceUserIds = channelStates.get(channelId);
            Map<Long, UserRead> userReads = new HashMap<>();
            for (Long voiceUserId : voiceUserIds) {
                UserRead findUserRead = findUserReadIdAndGuildId(voiceUserId, guildId);
                userReads.put(findUserRead.getUserId(), findUserRead);
            }
            voiceChannelStates.put(channelId, userReads);
        }
        return voiceChannelStates;
    }
}