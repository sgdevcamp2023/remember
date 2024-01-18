package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.community.command.dto.RoomRegistrationRequestDto;
import harmony.communityservice.community.command.repository.RoomCommandRepository;
import harmony.communityservice.community.command.service.RoomCommandService;
import harmony.communityservice.community.command.service.RoomUserCommandService;
import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.mapper.ToRoomMapper;
import harmony.communityservice.community.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class RoomCommandServiceImpl implements RoomCommandService {

    private final RoomCommandRepository roomCommandRepository;
    private final ContentService contentService;
    private final UserQueryService userQueryService;
    private final RoomUserCommandService roomUserCommandService;

    @Override
    public void save(RoomRegistrationRequestDto roomRegistrationRequestDto, MultipartFile image) {
        String profileUrl = contentService.imageConvertUrl(image);
        Room room = ToRoomMapper.convert(roomRegistrationRequestDto, profileUrl);
        roomCommandRepository.save(room);
        roomRegistrationRequestDto.getMembers().stream()
                .map(userQueryService::findUser)
                .forEach(user -> roomUserCommandService.save(room, user));
    }
}
