package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RoomRegistrationRequestDto;
import harmony.communityservice.community.domain.Room;

public class ToRoomMapper {

    public static Room convert(RoomRegistrationRequestDto requestDto, String profileUrl) {
        return Room.builder()
                .name(requestDto.getName())
                .profile(profileUrl)
                .build();
    }
}
