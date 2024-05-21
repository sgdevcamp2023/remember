package harmony.communityservice.user.application.service;

import harmony.communityservice.user.application.port.in.RegisterUserCommand;
import harmony.communityservice.user.domain.User;

class UserMapper {

    static User convert(RegisterUserCommand registerUserCommand) {
        return User.builder()
                .userId(registerUserCommand.userId())
                .email(registerUserCommand.email())
                .profile(registerUserCommand.profile())
                .nickname(registerUserCommand.nickname())
                .build();
    }
}
