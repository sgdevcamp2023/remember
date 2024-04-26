package harmony.communityservice.user.service.command.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.UserId;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.user.dto.RegisterUserReadRequest;
import harmony.communityservice.user.mapper.ToUserReadMapper;
import harmony.communityservice.user.repository.command.UserCommandRepository;
import harmony.communityservice.user.repository.command.UserReadCommandRepository;
import harmony.communityservice.user.service.command.UserReadCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class UserReadCommandServiceImpl implements UserReadCommandService {
    private final UserCommandRepository userCommandRepository;
    private final UserReadCommandRepository userReadCommandRepository;


    @Override
    public void register(RegisterUserReadRequest registerUserReadRequest) {
        User targetUser = userCommandRepository.findById(UserId.make(registerUserReadRequest.userId()))
                .orElseThrow(NotFoundDataException::new);
        UserRead userRead = ToUserReadMapper.convert(registerUserReadRequest, targetUser);
        userReadCommandRepository.save(userRead);
    }

    @Override
    public void modifyUserNicknameInGuild(ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest) {
        UserRead targetUserRead = userReadCommandRepository.findByUserIdAndGuildId(
                UserId.make(modifyUserNicknameInGuildRequest.userId()),
                GuildId.make(modifyUserNicknameInGuildRequest.guildId())).orElseThrow(NotFoundDataException::new);
        targetUserRead.modifyNickname(modifyUserNicknameInGuildRequest.nickname());
    }

    @Override
    public UserRead searchByUserIdAndGuildId(Long userId, Long guildId) {
        return userReadCommandRepository.findByUserIdAndGuildId(UserId.make(userId), GuildId.make(guildId))
                .orElseThrow(NotFoundDataException::new);
    }
}
