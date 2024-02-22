package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.RoomDeleteRequestDto;
import harmony.communityservice.community.command.dto.RoomRegistrationRequestDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface RoomCommandService {

    void save(RoomRegistrationRequestDto roomRegistrationRequestDto);

    void delete(RoomDeleteRequestDto RoomDeleteRequestDto);
}
